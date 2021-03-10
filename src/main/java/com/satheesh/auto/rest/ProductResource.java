package com.satheesh.auto.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satheesh.auto.dao.ProductDao;
import com.satheesh.auto.model.Product;

@RestController
@RequestMapping("rest")
public class ProductResource {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	ProductDao productDao;

	@GetMapping("products")
	public ResponseEntity<?> getProducts(@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		try {
			logger.info("Fetching products from database...");
			List<Product> products = productDao.getProducts(productName, page, size);
			ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			logger.error("Exception occurred while getting product : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
