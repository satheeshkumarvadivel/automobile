package com.satheesh.auto.util;

import com.satheesh.auto.exceptions.ValidationException;
import com.satheesh.auto.model.Invoice;
import com.satheesh.auto.model.Product;

public class ValidationUtil {

	public static boolean isValidInvoice(Invoice invoice) throws ValidationException {

		if (invoice == null)
			throw new ValidationException("Invoice cannot be null");

		if (invoice.getCustomer() == null || invoice.getCustomer().getCustomerName() == null
				|| invoice.getCustomer().getCustomerName().trim().length() == 0)
			throw new ValidationException("Please provide customer name");

		if (invoice.getCustomer().getCustomerName().trim().length() > 150)
			throw new ValidationException("Customer name cannot be longer than 150 characters");

		if (containsInvalidCharacters(invoice.getCustomer().getCustomerName()))
			throw new ValidationException("Customer name contains invalid characters");

		if (invoice.getNextOilService() != null && invoice.getNextOilService().trim().length() > 0) {
			if (invoice.getNextOilService().trim().length() > 100)
				throw new ValidationException("Next Oil Service cannot be longer than 100 characters");

			if (containsInvalidCharacters(invoice.getNextOilService()))
				throw new ValidationException("Next Oil Service contains invalid characters");
		}

		if (invoice.getVehicleNumber() != null && invoice.getVehicleNumber().trim().length() > 0) {
			if (invoice.getVehicleNumber().trim().length() > 20)
				throw new ValidationException("Vehicle Number cannot be longer than 20 characters");

			if (containsInvalidCharacters(invoice.getVehicleNumber().trim()))
				throw new ValidationException("Vehicle Number contains invalid characters");
		}

		if (invoice.getProducts() == null || invoice.getProducts().size() == 0)
			throw new ValidationException("Atleast one product must be present");

		for (Product product : invoice.getProducts()) {
			if (product == null)
				throw new ValidationException("Product cannot be null");
			if (product.getProductName() == null || product.getProductName().trim().length() == 0)
				throw new ValidationException("Product must have a name");
			if (product.getProductName().trim().length() > 250)
				throw new ValidationException("Product name cannot be longer than 250 characters");
			if (containsInvalidCharacters(product.getProductName().trim()))
				throw new ValidationException("Product name contains invalid characters");
			if (product.getQuantity() <= 0)
				product.setQuantity(1);
		}

		if (invoice.getTotal() < 0)
			throw new ValidationException("Invalid total value provided");

		return true;
	}

	public static boolean containsInvalidCharacters(String str) {
		return (str.contains("<") || str.contains("$") || str.contains("'") || str.contains("\"") 
				|| str.contains("%"));
	}

	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
