package com.satheesh.auto.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satheesh.auto.dao.InvoiceDao;
import com.satheesh.auto.exceptions.ValidationException;
import com.satheesh.auto.model.Invoice;
import com.satheesh.auto.model.PaginatedListResponse;
import com.satheesh.auto.model.SimpleResponse;
import com.satheesh.auto.util.ValidationUtil;

@RestController
@RequestMapping("rest")
public class InvoiceResource {

	private Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	InvoiceDao invoiceDao;

	@GetMapping("invoices")
	public ResponseEntity<?> getInvoice(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		try {
			logger.info("Fetching invoice from database...");
			PaginatedListResponse<Invoice> invoices = invoiceDao.getInvoices(search, page, size);
			ResponseEntity<PaginatedListResponse<Invoice>> response = new ResponseEntity<>(invoices, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			logger.error("Exception occurred while getting invoice : ", e);
			return new ResponseEntity<>(new SimpleResponse(500, "ERROR: Unable to get Invoice."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("invoices/{invoiceId}")
	public ResponseEntity<?> getInvoiceById(@PathVariable(value = "invoiceId") Integer invoiceId) {
		try {
			logger.info("Fetching invoice from database...");
			Invoice invoice = invoiceDao.getInvoiceById(invoiceId);
			ResponseEntity<Invoice> response = new ResponseEntity<>(invoice, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			logger.error("Exception occurred while getting invoice : ", e);
			return new ResponseEntity<>(new SimpleResponse(500, "ERROR: Unable to get Invoice."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("invoices")
	public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {

		try {

			ValidationUtil.isValidInvoice(invoice);

			invoiceDao.createInvoice(invoice);

			return new ResponseEntity<>(new SimpleResponse(201, "Invoice created successfully"), HttpStatus.CREATED);
		} catch (ValidationException valEx) {
			logger.error("Exception occurred while creating invoice : ", valEx);
			return new ResponseEntity<>(new SimpleResponse(400, valEx.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occurred while creating invoice : ", e);
			return new ResponseEntity<>(new SimpleResponse(500, "ERROR: Unable to create Invoice."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
