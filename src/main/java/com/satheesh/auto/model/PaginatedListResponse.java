package com.satheesh.auto.model;

import java.util.List;

public class PaginatedListResponse<T> {

	int page;
	int size;
	int totalPages;

	List<T> data;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "PaginatedListResponse [page=" + page + ", size=" + size + ", totalPages=" + totalPages + ", data="
				+ data + "]";
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
