package io.homebird.api.validation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * HomebirdObject
 *
 * @author Anthony DePalma
 */
@Data
public class HomebirdObject {

	@Range(min = 5, max = 10)
	private int size;

	@NotNull
	private String value;

	@NotNull
	private HomebirdNestedObject nestedObject = new HomebirdNestedObject();

	/**
	 * HomebirdNestedObject
	 *
	 * @author Anthony DePalma
	 */
	public static class HomebirdNestedObject {

		@NotNull
		private String nestedValue;

		// getters and setters
		public String getNestedValue() {
			return nestedValue;
		}

		public void setNestedValue(String nestedValue) {
			this.nestedValue = nestedValue;
		}

	}


}
