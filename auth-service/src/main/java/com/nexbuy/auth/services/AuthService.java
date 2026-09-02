package com.nexbuy.auth.services;

import com.nexbuy.auth.dto.LoginRequest;
import com.nexbuy.auth.dto.LoginResponse;
import com.nexbuy.auth.dto.RegisterRequest;
import com.nexbuy.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
    	
    LoginResponse login(LoginRequest loginRequest);

}