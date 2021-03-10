package com.satheesh.auto.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class AuthResource {

	@GetMapping("/token")
	public ResponseEntity<?> getToken() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
