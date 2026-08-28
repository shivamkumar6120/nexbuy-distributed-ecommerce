package com.nexbuy.auth.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexbuy.auth.dto.RegisterRequest;
import com.nexbuy.auth.dto.RegisterResponse;
import com.nexbuy.auth.entity.User;
import com.nexbuy.auth.exception.EmailAlreadyExistsException;
import com.nexbuy.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	public final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    } 
	
	@Override
	public RegisterResponse register(RegisterRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail()));
		{
			throw new EmailAlreadyExistsException(
					"Email already exists"
					);
			
			User user = new User();
			user.setName(request.getName());
			user.setEmail(request.getEmail());
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			
			
			User savedUser = userRepository.save(user);
			
			return new RegisterResponse(
					savedUser.getId(),
					savedUser.getName(),
					savedUser.getEmail(),
					savedUser.getRole().name()
					);
			
		}
		
		
	}

}











