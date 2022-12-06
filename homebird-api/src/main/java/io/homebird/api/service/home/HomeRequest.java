package io.homebird.api.service.home;

import javax.validation.constraints.NotNull;

import io.homebird.api.service.user.AbstractUserRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * HomeRequest
 *
 * @author Anthony DePalma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomeRequest extends AbstractUserRequest {

	@NotNull
	private String name;

}
