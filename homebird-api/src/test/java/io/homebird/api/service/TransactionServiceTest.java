package io.homebird.api.service;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.homebird.api.HomebirdApiTest;
import io.homebird.api.service.user.UserRequest;
import io.homebird.api.service.user.UserService;
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
		String email = "user@email.com";
		String password = "password";

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.publicTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});
	}

	@Test
	public void protectedTransaction() {
		String email = "user@email.com";
		String password = "password";

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.protectedTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});
	}

	@Test
	public void privateTransaction() {
		String email = "user@email.com";
		String password = "password";

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.privateTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});
	}

	@Test
	public void finalTransaction() {
		String email = "user@email.com";
		String password = "password";

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});

		assertThrows(ValidationException.class, () -> {
			UserRequest request = new UserRequest("user@email.com", "password");
			transactionService.finalTransaction(request);
		});

		assertThrows(ValidationException.class, () -> {
			userService.authenticateUser(email, password);
		});
	}

}
