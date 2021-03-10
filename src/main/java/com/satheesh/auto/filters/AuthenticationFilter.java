package com.satheesh.auto.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Bypass authentication for unauthenticated resources
		if (isUnauthenticatedResource(httpRequest)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}

		String authorizationHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);

		if (authorizationHeader == null || authorizationHeader.trim().length() == 0) {
			httpResponse.sendError(401);
			return;
		}

		if (isValidCredentials(authorizationHeader)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		} else {
			httpResponse.sendError(401);
			return;
		}

	}

	private boolean isValidCredentials(String authorizationHeader) throws IOException {
		try {
			String[] encoded = authorizationHeader.split(" ");
			byte[] decoded = Base64Utils.decodeFromString(encoded[1]);
			String[] creds = new String(decoded, StandardCharsets.UTF_8).split(":");

			if ("satheesh".equals(creds[0]) && "password".equals(creds[1])) {
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception during authentication : ", e);
		}
		return false;
	}

	private boolean isUnauthenticatedResource(HttpServletRequest httpRequest) {
		if (httpRequest.getRequestURI().startsWith("/rest/")) {
			return false;
		}
		return true;
	}

}
