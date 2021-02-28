package com.satheesh.auto.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satheesh.auto.model.Customer;
import com.satheesh.auto.model.Invoice;
import com.satheesh.auto.model.PaginatedListResponse;
import com.satheesh.auto.model.Product;

@Repository
public class InvoiceDao {

	@Autowired
	JdbcTemplate jdbc;

	private Logger logger = LogManager.getLogger(this.getClass());

	public PaginatedListResponse<Invoice> getInvoices(String search, int page, int size) {

		List<Invoice> invoices = new ArrayList<>();
		int limit = (size <= 0) ? 10 : size;
		int offset = (page <= 1) ? 0 : (page - 1) * limit;

		List<Map<String, Object>> results;
		String sql = "select inv.id as invoice_id, inv.invoice_date, cust.customer_name, inv.next_oil_service, inv.vehicle_number, inv.remarks, "
				+ "inv.total as invoice_total from invoice inv join invoice_customer cust on inv.id = cust.invoice_id ";

		if (search == null || search.trim().length() <= 0) {
			sql += " WHERE inv.invoice_date  >=  current_timestamp - interval '60 day' ORDER BY inv.invoice_date DESC LIMIT ? OFFSET ?";
			results = jdbc.queryForList(sql, limit, offset);
		} else {
			sql += " WHERE lower(cust.customer_name) like ? ORDER BY invoice_date DESC LIMIT ? OFFSET ?";
			results = jdbc.queryForList(sql, "%" + search.trim().toLowerCase() + "%", limit, offset);
		}

		logger.info("Query : " + sql);

		if (results != null) {
			for (Map<String, Object> map : results) {
				int invoiceNumber = (Integer) map.get("invoice_id");

				Invoice invoice = new Invoice();
				invoice.setInvoiceNumber(String.valueOf(invoiceNumber));

				String invoiceDate = String.valueOf(map.get("invoice_date"));
				invoice.setInvoiceDate(invoiceDate.split(" ")[0]);

				invoice.setNextOilService(String.valueOf(map.get("next_oil_service")));
				invoice.setVehicleNumber(String.valueOf(map.get("vehicle_number")));
				invoice.setRemarks(String.valueOf(map.get("remarks")));

				BigDecimal invoiceTotal = (BigDecimal) map.get("invoice_total");
				invoice.setTotal(invoiceTotal.floatValue());

				Customer customer = new Customer();
				customer.setCustomerName(String.valueOf(map.get("customer_name")));
				invoice.setCustomer(customer);

				invoices.add(invoice);

			}
		}

		String countSql = "SELECT COUNT(*) FROM invoice WHERE invoice_date  >=  current_timestamp - interval '60 day'";

		int count = jdbc.queryForObject(countSql, Integer.class);

		PaginatedListResponse<Invoice> response = new PaginatedListResponse<Invoice>();
		response.setData(invoices);
		response.setPage(page);
		response.setSize(size);
		int totalPages = (count % size) == 0 ? (count / size) : (count / size) + 1;
		response.setTotalPages(totalPages);

		return response;
	}

	public Invoice getInvoiceById(int invoiceId) {

		String invoice_sql = "SELECT inv.id, inv.invoice_date, cust.customer_name, inv.vehicle_number, inv.next_oil_service, inv.remarks, inv.total FROM invoice inv JOIN invoice_customer cust ON inv.id = cust.invoice_id WHERE inv.id = ? limit 1";
		List<Map<String, Object>> results;
		results = jdbc.queryForList(invoice_sql, invoiceId);
		Invoice invoice = new Invoice();

		if (results != null) {
			for (Map<String, Object> map : results) {
				invoice.setInvoiceNumber(String.valueOf((Integer) map.get("id")));
				String invoiceDate = String.valueOf(map.get("invoice_date"));
				invoice.setInvoiceDate(invoiceDate.split(" ")[0]);

				invoice.setNextOilService(String.valueOf(map.get("next_oil_service")));
				invoice.setVehicleNumber(String.valueOf(map.get("vehicle_number")));
				invoice.setRemarks(String.valueOf(map.get("remarks")));

				BigDecimal invoiceTotal = (BigDecimal) map.get("total");
				invoice.setTotal(invoiceTotal.floatValue());

				Customer customer = new Customer();
				customer.setCustomerName(String.valueOf(map.get("customer_name")));
				invoice.setCustomer(customer);
			}
		}

		String product_sql = "SELECT product_name, total from invoice_product where invoice_id = ?";

		results = jdbc.queryForList(product_sql, invoiceId);

		if (results != null) {
			List<Product> productsList = new ArrayList<>();
			for (Map<String, Object> map : results) {
				Product product = new Product();
				product.setProductName(String.valueOf(map.get("product_name")));

				BigDecimal total = (BigDecimal) map.get("total");
				product.setTotal(total.floatValue());
				productsList.add(product);
			}
			invoice.setProducts(productsList);
		}
		return invoice;
	}

	@Transactional
	public void createInvoice(Invoice invoice) {
		int invoiceId = addInvoice(invoice);
		addInvoiceProducts(invoice, invoiceId);
		addInvoiceCustomer(invoice, invoiceId);
		addInvoiceAddress(invoice, invoiceId);
	}

	private int addInvoice(Invoice invoice) {
		String sql = "INSERT INTO invoice (total, mode_of_payment, remarks, vehicle_number, next_oil_service) VALUES (?, ?, ?, ?, ?)";
		logger.info("Query : " + sql);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setBigDecimal(1, new BigDecimal(invoice.getTotal()).setScale(0, RoundingMode.DOWN));
				ps.setString(2, invoice.getModeOfPayment());
				ps.setString(3, invoice.getRemarks());
				ps.setString(4, invoice.getVehicleNumber());
				ps.setString(5, invoice.getNextOilService());
				return ps;
			}
		}, keyHolder);

		return (Integer) keyHolder.getKeys().get("id");
	}

	private void addInvoiceProducts(Invoice invoice, int invoice_id) {
		String products_sql = "INSERT INTO invoice_product (invoice_id, product_code, product_name, description, price, quantity, igst, cgst, sgst, discount, total) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		jdbc.batchUpdate(products_sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {

				ps.setInt(1, invoice_id);
				ps.setString(2, invoice.getProducts().get(i).getProductCode());
				ps.setString(3, invoice.getProducts().get(i).getProductName());
				ps.setString(4, invoice.getProducts().get(i).getDescription());

				float price = invoice.getProducts().get(i).getPrice();
				int quantity = invoice.getProducts().get(i).getQuantity();
				quantity = (quantity <= 0) ? 1 : quantity;
				int igst = invoice.getProducts().get(i).getIgst();
				int cgst = invoice.getProducts().get(i).getCgst();
				int sgst = invoice.getProducts().get(i).getSgst();
				float discount = invoice.getProducts().get(i).getDiscount();

				ps.setBigDecimal(5, new BigDecimal(price).setScale(0, RoundingMode.DOWN));
				ps.setInt(6, quantity);
				ps.setInt(7, igst);
				ps.setInt(8, cgst);
				ps.setInt(9, sgst);
				ps.setBigDecimal(10, new BigDecimal(discount).setScale(0, RoundingMode.DOWN));

				float total = price * quantity;
				total = total - (total * discount / 100);
				total = total + (total * igst / 100) + (total * cgst / 100) + (total * sgst / 100);

				ps.setBigDecimal(11, new BigDecimal(total).setScale(0, RoundingMode.DOWN));

			}

			@Override
			public int getBatchSize() {
				return invoice.getProducts().size();
			}
		});
	}

	public void addInvoiceCustomer(Invoice invoice, int invoiceId) {
		String customer_sql = "INSERT INTO invoice_customer (invoice_id, customer_name, company_name, contact_number, email, gst_number) VALUES (?,?,?,?,?,?)";

		jdbc.update(customer_sql, invoiceId, invoice.getCustomer().getCustomerName(),
				invoice.getCustomer().getCompanyName(), invoice.getCustomer().getContactNumber(),
				invoice.getCustomer().getEmail(), invoice.getCustomer().getGstNumber());
	}

	public void addInvoiceAddress(Invoice invoice, int invoiceId) {
		String address_sql = "INSERT INTO invoice_address (invoice_id, address_line1, address_line2, city, state, state_code, contact_number, address_type) VALUES (?,?,?,?,?,?,?,?)";
		if (invoice.getBillingAddress() != null) {
			jdbc.update(address_sql, invoiceId, invoice.getBillingAddress().getAddressLine1(),
					invoice.getBillingAddress().getAddressLine2(), invoice.getBillingAddress().getCity(),
					invoice.getBillingAddress().getState(), invoice.getBillingAddress().getStateCode(),
					invoice.getBillingAddress().getContactNumber(), invoice.getBillingAddress().getAddressType());
		}

		if (invoice.getShippingAddress() != null) {
			jdbc.update(address_sql, invoiceId, invoice.getShippingAddress().getAddressLine1(),
					invoice.getShippingAddress().getAddressLine2(), invoice.getShippingAddress().getCity(),
					invoice.getShippingAddress().getState(), invoice.getShippingAddress().getStateCode(),
					invoice.getShippingAddress().getContactNumber(), invoice.getShippingAddress().getAddressType());
		}
	}

}
