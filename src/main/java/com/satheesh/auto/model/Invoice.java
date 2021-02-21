package com.satheesh.auto.model;

import java.util.List;

public class Invoice {

	String invoiceNumber;
	String invoiceDate;
	List<Product> products;
	Customer customer;
	Address billingAddress;
	Address shippingAddress;
	float total;
	String nextOilService;
	String vehicleNumber;
	String modeOfPayment;
	String remarks;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
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

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", products=" + products
				+ ", customer=" + customer + ", billingAddress=" + billingAddress + ", shippingAddress="
				+ shippingAddress + ", total=" + total + ", nextOilService=" + nextOilService + ", vehicleNumber="
				+ vehicleNumber + ", modeOfPayment=" + modeOfPayment + ", remarks=" + remarks + "]";
	}

}
