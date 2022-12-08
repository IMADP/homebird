package io.homebird.api.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.homebird.api.HomebirdApiTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * UserServiceTest
 *
 * @author Anthony DePalma
 */
public class UserServiceTest extends HomebirdApiTest {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void createUser() {

		// create a valid user
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("email@email.com");
		userRequest.setPassword("password");
		User user = userService.createUser(userRequest, UserAuthority.ROLE_USER);

		// create an auth request
		assertNotNull(user);
		assertNotNull(user.getId());
		assertNotNull(user.getResetToken());
		assertNotNull(user.getVerifyToken());
		assertNotNull(user.getPasswordDate());
		assertNotNull(user.getTimeCreated());
		assertNotNull(user.getTimeModified());
		assertEquals(0L, user.getVersion());
		assertEquals(UserAuthority.ROLE_USER, user.getAuthority());
		assertEquals(userRequest.getEmail(), user.getEmail());
		assertTrue(passwordEncoder.matches(userRequest.getPassword(), user.getPassword()));
	}

	@Test
	public void getToken() {

		// create a valid user
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("email@email.com");
		userRequest.setPassword("password");
		User user = userService.createUser(userRequest, UserAuthority.ROLE_USER);

		// create a token request
		UserTokenRequest userTokenRequest = new UserTokenRequest();
		userTokenRequest.setEmail(userRequest.getEmail());
		userTokenRequest.setPassword(userRequest.getPassword());
		userTokenRequest.setLongExpire(false);

		// get the token
		String token = userService.getToken(userTokenRequest).getToken();
		Jws<Claims> jws = userService.parseToken(token);
		UserClaim claim = jws.getBody().get(UserClaim.KEY, UserClaim.class);

		assertEquals(UserAuthority.ROLE_USER, claim.getAuthority());
		assertEquals(user.getId(), claim.getUserId());
		assertEquals(user.getPasswordDate().getEpochSecond(), claim.getPasswordDate());

		Optional<Boolean> valid = userService.validateToken(jws);

		assertTrue(valid.isEmpty());
	}

}
