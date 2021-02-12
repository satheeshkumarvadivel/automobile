package com.satheesh.auto.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.satheesh.auto.exceptions.ValidationException;
import com.satheesh.auto.model.Invoice;
import com.satheesh.auto.model.SimpleResponse;
import com.satheesh.auto.model.SimpleSuccessResponse;
import com.satheesh.auto.util.ValidationUtil;

public class ProductResource {

	private Logger logger = LogManager.getLogger(this.getClass());

	@PostMapping("invoice")
	public ResponseEntity<?> createProduct(@RequestBody Invoice invoice) {
		try {
			logger.info(invoice.toString());
			ValidationUtil.isValidInvoice(invoice);
			
			

			return new ResponseEntity<>(new SimpleSuccessResponse("Invoice created successfully."), HttpStatus.CREATED);
		} catch (ValidationException valEx) {
			logger.debug(valEx.getMessage());
			return new ResponseEntity<>(new SimpleResponse(400, valEx.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while saving invoice : ", e);
			return new ResponseEntity<>(new SimpleResponse(500, "ERROR: Unable to create Invoice."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
