package io.homebird.api.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * HomebirdObjectValidator
 *
 * @author Anthony DePalma
 */
@Component
public class HomebirdObjectValidator extends Validator<HomebirdObject> {

	@Autowired
	private Validator<Object> validator;

	@Override
	protected void doValidation(HomebirdObject object, ValidationContext errors) {
		super.doValidation(object, errors);
		validator.validateNested(object.getNestedObject(), "nestedObject", errors);
	}

}
