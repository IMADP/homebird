package io.homebird.api.service.auth;

import io.homebird.api.service.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * AuthResponse
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
public class AuthResponse {

	private String token;

	private UserResponse user;

}