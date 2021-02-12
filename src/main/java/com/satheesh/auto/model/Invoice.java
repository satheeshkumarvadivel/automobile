package com.satheesh.auto.model;

import java.util.List;

public class Invoice {

	String invoiceNumber;
	List<Product> products;
	String total;
	String customerName;
	String nextOilService;
	String vehicleNumber;
	String invoiceDate;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNextOilService() {
		return nextOilService;
	}

	public void setNextOilService(String nextOilService) {
		this.nextOilService = nextOilService;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceNumber=" + invoiceNumber + ", products=" + products + ", total=" + total
				+ ", customerName=" + customerName + ", nextOilService=" + nextOilService + ", vehicleNumber="
				+ vehicleNumber + ", invoiceDate=" + invoiceDate + "]";
	}

}
