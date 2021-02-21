package com.satheesh.auto.model;

public class Customer {

	String customerName;
	String companyName;
	String contactNumber;
	String email;
	String gstNumber;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	@Override
	public String toString() {
		return "Customer [customerName=" + customerName + ", companyName=" + companyName + ", contactNumber="
				+ contactNumber + ", email=" + email + ", gstNumber=" + gstNumber + "]";
	}

}
