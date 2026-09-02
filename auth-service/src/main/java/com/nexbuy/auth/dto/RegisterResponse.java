package com.nexbuy.auth.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

	private Long id;
	private String name;
	private String email;
	private String role;
}
