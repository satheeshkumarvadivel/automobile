package com.satheesh.auto.model;

public class Product {

	int productId;
	String productCode;
	String productName;
	String description;
	float price;
	int quantity;
	int igst;
	int cgst;
	int sgst;
	float discount;
	float total;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getIgst() {
		return igst;
	}

	public void setIgst(int igst) {
		this.igst = igst;
	}

	public int getCgst() {
		return cgst;
	}

	public void setCgst(int cgst) {
		this.cgst = cgst;
	}

	public int getSgst() {
		return sgst;
	}

	public void setSgst(int sgst) {
		this.sgst = sgst;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", description=" + description + ", price=" + price + ", quantity=" + quantity + ", igst=" + igst
				+ ", cgst=" + cgst + ", sgst=" + sgst + ", discount=" + discount + ", total=" + total + "]";
	}

}
