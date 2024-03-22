package com.coderscampus.SpringSecurityJWTDemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping("/error")
	public String getErrorMessage () {
		return "error/error";
	}
	
	@GetMapping("/userExists")
	public String getUserExistsMessage () {
		return "error/userExists";
	}
}
