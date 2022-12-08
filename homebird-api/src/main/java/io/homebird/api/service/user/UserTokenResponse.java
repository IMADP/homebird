package io.homebird.api.service.user;

import lombok.Getter;
import lombok.Setter;

/**
 * UserTokenResponse
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
public class UserTokenResponse {

	private String token;

	private UserResponse user;

}