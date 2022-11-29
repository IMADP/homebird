package io.homebird.api.service.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.homebird.api.HomebirdApiTest;
import io.homebird.api.service.user.User;
import io.homebird.api.service.user.UserAuthority;

/**
 * AuthClaimTest
 * 
 * @author Anthony DePalma
 */
public class AuthClaimTest extends HomebirdApiTest {

	@Test
	public void isMatch() {
		User user = new User();
		user.setPassword("password");
		user.setPasswordDate(Instant.now());
		user.setAuthority(UserAuthority.ROLE_USER);

		AuthClaim claim = new AuthClaim(user, false);
		assertTrue(claim.isMatch(user));
	}

	@Test
	public void noMatchPasswordDate() {
		User user = new User();
		user.setPassword("password");
		user.setPasswordDate(Instant.now());
		user.setAuthority(UserAuthority.ROLE_USER);

		AuthClaim claim = new AuthClaim(user, false);
		assertTrue(claim.isMatch(user));

		user.setPasswordDate(Instant.now().plusSeconds(10));
		assertFalse(claim.isMatch(user));
	}

	@Test
	public void noMatchAuthority() {
		User user = new User();
		user.setPassword("password");
		user.setPasswordDate(Instant.now());
		user.setAuthority(UserAuthority.ROLE_USER);

		AuthClaim claim = new AuthClaim(user, false);
		assertTrue(claim.isMatch(user));

		user.setAuthority(UserAuthority.ROLE_GUEST);
		assertFalse(claim.isMatch(user));
	}

	@Test
	public void noMatchId() {
		User user = new User();
		user.setPassword("password");
		user.setPasswordDate(Instant.now());
		user.setAuthority(UserAuthority.ROLE_USER);

		AuthClaim claim = new AuthClaim(user, false);
		assertTrue(claim.isMatch(user));

		user.setId(UUID.randomUUID());
		assertFalse(claim.isMatch(user));
	}

}
