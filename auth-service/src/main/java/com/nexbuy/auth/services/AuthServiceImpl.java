package com.nexbuy.auth.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexbuy.auth.config.JWTService;
import com.nexbuy.auth.dto.LoginRequest;
import com.nexbuy.auth.dto.LoginResponse;
import com.nexbuy.auth.dto.RegisterRequest;
import com.nexbuy.auth.dto.RegisterResponse;
import com.nexbuy.auth.entity.Role;
import com.nexbuy.auth.entity.User;
import com.nexbuy.auth.exception.EmailAlreadyExistsException;
import com.nexbuy.auth.exception.InvalidCredentialsException;
import com.nexbuy.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTService jwtService;

	@Override
	public RegisterResponse register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);

		User savedUser = userRepository.save(user);

		return new RegisterResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
				savedUser.getRole().name());
	}

	@Override
	public LoginResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid Email or Password"));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}

		String token = jwtService.generateToken(
				user.getEmail(), 
				user.getRole().name());

		return new LoginResponse(token);
	}

}
