package io.homebird.api.service.home;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import io.homebird.api.HomebirdApiTest;
import io.homebird.api.service.auth.AuthClaim;
import io.homebird.api.service.user.User;
import io.homebird.api.service.user.UserAuthority;
import io.homebird.api.service.user.UserRequest;
import io.homebird.api.service.user.UserService;

/**
 * HomeServiceTest
 *
 * @author Anthony DePalma
 */
public class HomeServiceTest extends HomebirdApiTest {

	@Autowired
	private UserService userService;

	@Autowired
	private HomeService homeService;

	@Test
	public void createHome() {

		// create a user
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("email@email.com");
		userRequest.setPassword("password");
		User user = userService.createUser(userRequest, UserAuthority.ROLE_USER);

		// set authentication
		AuthClaim claim = new AuthClaim(user, false);
		List<GrantedAuthority> authorities = Arrays.asList(claim.getAuthority());
		Authentication authentication = new UsernamePasswordAuthenticationToken(claim, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// create a home
		HomeRequest homeRequest = new HomeRequest();
		homeRequest.setName("name");
		homeRequest.setUserId(user.getId());
		Home home = homeService.createHome(homeRequest);

		assertNotNull(home);
		assertNotNull(home.getId());
		assertNotNull(home.getName());
	}

}
