package io.homebird.api.service.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthController
 *
 * @author Anthony DePalma
 */
@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

	// properties
	private final AuthService authService;

	@PostMapping("login")
	public AuthResponse login(@RequestBody AuthRequest authRequest) {
		log.info("Get token for user [{}]", authRequest.getUsername());
		return authService.authenticate(authRequest);
	}

}