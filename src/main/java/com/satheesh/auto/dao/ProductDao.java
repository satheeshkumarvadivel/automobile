package com.satheesh.auto.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.satheesh.auto.exceptions.DbException;
import com.satheesh.auto.model.Product;

@Repository
public class ProductDao {

	@Autowired
	JdbcTemplate jdbc;

	private Logger logger = LogManager.getLogger(this.getClass());

	public List<Product> getProducts(String productName, int page, int size) throws DbException {
		List<Product> products = new ArrayList<>();
		int limit = (size <= 0) ? 10 : size;
		int offset = (page <= 1) ? 0 : (page - 1) * limit;

		List<Map<String, Object>> results;
		String sql = "SELECT * FROM product where is_active = true ";

		if (productName == null || productName.trim().length() == 0) {
			sql += " order by product_name limit ? offset ?";
			results = jdbc.queryForList(sql, limit, offset);
		} else {
			productName = "%" + productName.trim().toLowerCase() + "%";
			sql += " and lower(product_name) like ? order by product_name limit ? offset ?";
			results = jdbc.queryForList(sql, productName, limit, offset);
		}

		logger.info("Query : " + sql);

		if (results != null) {
			for (Map<String, Object> map : results) {
				Product product = new Product();
				int product_id = (Integer) map.get("id");
				product.setProductId(product_id);

				String product_name = String.valueOf(map.get("product_name"));
				product.setProductName(product_name);

				product.setDescription(String.valueOf(map.get("description")));

				BigDecimal price = (BigDecimal) map.get("price");
				product.setPrice(price.floatValue());

				products.add(product);

			}
		}
		return products;
	}
}
