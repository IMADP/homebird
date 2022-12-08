package io.homebird.api.service.user;

import lombok.Getter;
import lombok.Setter;

/**
 * UserToken
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
public class UserToken {

	private User user;

	private String token;

}