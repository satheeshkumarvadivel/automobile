package com.satheesh.auto;

class AutoApplicationTests {

	
	public static void main(String a[]) {

		String EXCLUDE_URL = ".*(css|jpg|png|gif|js|html)";

		String url = "https://www.google.co.in/images/test.js";

		System.out.println(url.matches(EXCLUDE_URL));

	}

}
