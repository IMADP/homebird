package io.homebird.api.service.home;

import io.homebird.api.service.AbstractEntityResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * HomeResponse
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
public class HomeResponse extends AbstractEntityResponse {

	private String name;

}