package io.homebird.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.homebird.api.service.user.UserAuthority;
import io.homebird.api.service.user.UserRequest;
import io.homebird.api.service.user.UserService;

/**
 * TransactionService
 *
 * @author Anthony DePalma
 */
@Service
public class TransactionService {

	@Autowired
	private UserService userService;

	@Transactional
	public void publicTransaction(UserRequest request) {
		userService.createUser(request, UserAuthority.ROLE_USER);
		userService.createUser(request, UserAuthority.ROLE_USER);
	}

	@Transactional
	protected void protectedTransaction(UserRequest request) {
		userService.createUser(request, UserAuthority.ROLE_USER);
		userService.createUser(request, UserAuthority.ROLE_USER);
	}

	public void privateTransaction(UserRequest request) {
		selfInvokedTransaction(request);
	}

	@Transactional
	private void selfInvokedTransaction(UserRequest request) {
		userService.createUser(request, UserAuthority.ROLE_USER);
		userService.createUser(request, UserAuthority.ROLE_USER);
	}

	@Transactional
	public final void finalTransaction(UserRequest request) {
		userService.createUser(request, UserAuthority.ROLE_USER);
		userService.createUser(request, UserAuthority.ROLE_USER);
	}

}