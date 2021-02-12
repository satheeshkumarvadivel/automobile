//package com.satheesh.auto.dao;
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import com.satheesh.auto.exceptions.DbException;
//import com.satheesh.auto.model.Invoice;
//
//@Repository
//public class InvoiceDao {
//
//	@Autowired
//	JdbcTemplate jdbc;
//
//	private Logger logger = LogManager.getLogger(this.getClass());
//
//	private int addInvoice(Invoice invoice) {
//		String sql = "INSERT INTO invoice (customer_id, price, payment_received) VALUES (?, ?, ?)";
//		logger.info("Query : " + sql);
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//
//		jdbc.update(new PreparedStatementCreator() {
//			@Override
//			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//				ps.setString(1, invoice.getCustomerName());
//				ps.setBigDecimal(2, new BigDecimal(invoice.getPrice()));
//				ps.setBigDecimal(3, new BigDecimal(invoice.getPayment_received()));
//				return ps;
//			}
//		}, keyHolder);
//
//		return (Integer) keyHolder.getKeys().get("id");
//	}
//
//	private void addInvoiceItems(Invoice invoice, int invoice_id) {
//		String items_sql = "INSERT INTO invoice_item (invoice_id, customer_id, product_id, quantity, price, total) VALUES (?, ?, ?, ?, ?, ?)";
//
//		jdbc.batchUpdate(items_sql, new BatchPreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setInt(1, invoice_id);
//				ps.setInt(2, customer_id);
//				ps.setInt(3, invoice.getItems().get(i).getProduct().getProduct_id());
//				ps.setInt(4, invoice.getItems().get(i).getQuantity());
//				ps.setBigDecimal(5, new BigDecimal(invoice.getItems().get(i).getPrice()));
//				ps.setBigDecimal(6, new BigDecimal(invoice.getItems().get(i).getTotal()));
//			}
//
//			@Override
//			public int getBatchSize() {
//				return invoice.getItems().size();
//			}
//		});
//	}
//
//	public List<Invoice> getCustomers(String search, int page, int size) throws DbException {
//		List<Invoice> customers = new ArrayList<>();
//		int limit = (size <= 0) ? 10 : size;
//		int offset = (page <= 1) ? 0 : (page - 1) * limit;
//		List<Map<String, Object>> results;
//
//		String sql = "SELECT * FROM customer where is_active = true ";
//		if (search == null || search.trim().length() == 0) {
//			sql += " order by company_name limit ? offset ?";
//			results = jdbc.queryForList(sql, limit, offset);
//		} else {
//			search = "%" + search.trim().toLowerCase() + "%";
//			sql += " and lower(company_name) like ? "
//					+ " OR lower(customer_name) like ? OR  lower(contact_number_1) like ? "
//					+ " OR lower(contact_number_1) like ? OR  lower(address) like ? "
//					+ " OR lower(email) like ? order by company_name limit ? offset ?";
//			results = jdbc.queryForList(sql, search, search, search, search, search, search, limit, offset);
//		}
//
//		logger.info("Query : " + sql);
//		if (results != null) {
//			for (Map<String, Object> map : results) {
//				int customer_id = (Integer) map.get("id");
//				String company_name = String.valueOf(map.get("company_name"));
//				Invoice customer = new Invoice(customer_id, company_name);
//				customer.setCustomer_name(String.valueOf(map.get("customer_name")));
//				customer.setContact_number_1(String.valueOf(map.get("contact_number_1")));
//				customer.setContact_number_2(String.valueOf(map.get("contact_number_2")));
//				customer.setAddress(String.valueOf(map.get("address")));
//				customer.setEmail(String.valueOf(map.get("email")));
//				BigDecimal outstanding_amount = (BigDecimal) map.get("outstanding_amount");
//				customer.setOutstanding_amount(outstanding_amount.floatValue());
//				customers.add(customer);
//			}
//		}
//		return customers;
//	}
//
//}
