package io.homebird.api.service.home;

import org.springframework.stereotype.Component;

import io.homebird.api.validation.ValidationContext;
import io.homebird.api.validation.Validator;
import lombok.RequiredArgsConstructor;

/**
 * HomeRequestValidator
 *
 * Validates a HomeRequest.
 *
 * @author Anthony DePalma
 */
@Component
@RequiredArgsConstructor
public class HomeRequestValidator extends Validator<HomeRequest> {

	// properties
	private final HomeRepository homeRepository;

	@Override
	protected void doValidation(HomeRequest request, ValidationContext context) {
		super.doValidation(request, context);

		if(request.getName() != null && homeRepository.findByName(request.getUserId(), request.getName()).isPresent()) {
			context.addFieldError("name", "inUse");
		}

	}

}