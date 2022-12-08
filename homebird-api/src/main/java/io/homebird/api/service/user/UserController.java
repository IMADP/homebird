package io.homebird.api.service.user;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;

import lombok.RequiredArgsConstructor;

/**
 * UserController
 *
 * @author Anthony DePalma
 */
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

	// properties
	private final UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> getUsers() {
		return userService.findUsers();
	}

	@PostMapping("token")
	public UserResponse getToken(@RequestBody UserTokenRequest request, HttpServletResponse response) {
		UserToken token = userService.getToken(request);
		response.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token.getToken()));
		return new UserResponse(token.getUser());
	}

	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest request) {
		User user = userService.createUser(request, UserAuthority.ROLE_USER);
		return new UserResponse(user);
	}

	@PostMapping("/{id}/send-verify-email")
	public void sendVerifyEmail(@PathVariable UUID id, @RequestBody UserEmailRequest request) {
		userService.sendVerifyEmail(id);
	}

	@PutMapping("/{id}/email")
	public UserResponse updateEmail(@PathVariable UUID id, @RequestBody UserEmailRequest request) {
		validateUserAccess(id);
		User user = userService.updateEmail(id, request);
		return new UserResponse(user);
	}

	@PutMapping("/{id}/password")
	public UserResponse updatePassword(@PathVariable UUID id, @RequestBody UserPasswordRequest request) {
		validateUserAccess(id);
		User user = userService.updatePassword(id, request);
		return new UserResponse(user);
	}

	/**
	 * Ensures that only users can modify their own data.
	 *
	 * @param id
	 */
	private void validateUserAccess(UUID id) {
		UserClaim claim = UserClaim.getCurrentClaim();

		// only admins can modify other users
		if(!claim.getAuthority().isAdmin() && !claim.getUserId().equals(id)) {
			throw new AccessDeniedException(String.valueOf(id));
		}
	}

}