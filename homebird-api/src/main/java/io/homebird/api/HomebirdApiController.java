package io.homebird.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomebirdApiController
 *
 * @author Anthony DePalma
 */
@Controller
public class HomebirdApiController {

	@GetMapping
	public String index() {
		return "redirect:/swagger-ui/";
	}

}