package io.homebird.api.service.user;

import org.springframework.stereotype.Component;

import io.homebird.api.validation.ValidationContext;
import io.homebird.api.validation.Validator;
import lombok.RequiredArgsConstructor;

/**
 * UserRequestValidator
 *
 * Validates a UserRequest.
 *
 * @author Anthony DePalma
 */
@Component
@RequiredArgsConstructor
public class UserRequestValidator extends Validator<UserRequest> {

	// properties
	private final UserRepository userRepository;

	@Override
	protected void doValidation(UserRequest request, ValidationContext context) {
		super.doValidation(request, context);

		if(request.getEmail() != null && userRepository.findByEmail(request.getEmail()).isPresent()) {
			context.addFieldError("email", "inUse");
		}

	}

}