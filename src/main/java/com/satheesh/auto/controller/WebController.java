package com.satheesh.auto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String getIndex() {
		return "index.html";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login.html";
	}

	@GetMapping("/billing")
	public String getBilling() {
		return "billing.html";
	}

	@GetMapping("/invoice")
	public String getInvoice() {
		return "invoice.html";
	}

}
