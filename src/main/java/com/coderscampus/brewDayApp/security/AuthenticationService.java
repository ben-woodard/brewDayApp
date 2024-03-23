package com.coderscampus.brewDayApp.security;

import com.coderscampus.brewDayApp.dao.request.SignInRequest;
import com.coderscampus.brewDayApp.dao.request.SignUpRequest;
import com.coderscampus.brewDayApp.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}