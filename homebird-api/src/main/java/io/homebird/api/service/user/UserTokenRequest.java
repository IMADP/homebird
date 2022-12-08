package io.homebird.api.service.user;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * UserTokenRequest
 *
 * Request object to obtain an authorization token.
 *
 * @author Anthony DePalma
 */
@Data
public class UserTokenRequest {

	@NotNull
	private String email;

	@NotNull
	private String password;

	private boolean longExpire;

}