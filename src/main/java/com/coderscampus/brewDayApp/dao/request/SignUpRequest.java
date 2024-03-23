package com.coderscampus.brewDayApp.dao.request;

import com.coderscampus.brewDayApp.domain.Product;
import java.util.List;
import java.util.Optional;

public record SignUpRequest(String email,
                            String password,
                            String firstName,
                            String lastName,
                            String companyName,
                            List<Product> products,
                            Optional<String> authorityOpt) {

}
