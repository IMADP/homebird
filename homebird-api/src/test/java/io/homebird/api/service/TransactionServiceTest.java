package io.homebird.api.service;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.homebird.api.HomebirdApiTest;
import io.homebird.api.service.user.UserRequest;
import io.homebird.api.service.user.UserService;
import io.homebird.api.service.user.UserTokenRequest;
import io.homebird.api.validation.ValidationException;

/**
 * TransactionServiceTest
 *
 * @author Anthony DePalma
 */
public class TransactionServiceTest extends HomebirdApiTest {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@Test
	public void publicTransaction() {
		UserTokenRequest tokenRequest = new UserTokenRequest();
		tokenRequest.setEmail("user@email.com");
		tokenRequest.setPassword("password");

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.publicTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});
	}

	@Test
	public void protectedTransaction() {
		UserTokenRequest tokenRequest = new UserTokenRequest();
		tokenRequest.setEmail("user@email.com");
		tokenRequest.setPassword("password");

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.protectedTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});
	}

	@Test
	public void privateTransaction() {
		UserTokenRequest tokenRequest = new UserTokenRequest();
		tokenRequest.setEmail("user@email.com");
		tokenRequest.setPassword("password");

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.privateTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});
	}

	@Test
	public void finalTransaction() {
		UserTokenRequest tokenRequest = new UserTokenRequest();
		tokenRequest.setEmail("user@email.com");
		tokenRequest.setPassword("password");

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.finalTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.getToken(tokenRequest);
		});
	}

}
