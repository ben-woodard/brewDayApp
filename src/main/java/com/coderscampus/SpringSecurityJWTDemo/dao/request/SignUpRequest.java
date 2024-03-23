package com.coderscampus.SpringSecurityJWTDemo.dao.request;

import com.coderscampus.SpringSecurityJWTDemo.domain.Product;

import java.util.List;
import java.util.Optional;

public record SignUpRequest(String email,
                            String password,
                            String firstName,
                            String lastName,
                            String companyName,
                            Optional<String> authorityOpt) {

}
