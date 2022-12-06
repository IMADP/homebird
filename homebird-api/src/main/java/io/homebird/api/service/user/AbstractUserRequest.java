package io.homebird.api.service.user;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * AbstractUserRequest
 *
 * @author Anthony DePalma
 */
@Data
public class AbstractUserRequest {

	@NotNull
	private UUID userId;

}
