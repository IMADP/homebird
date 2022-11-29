package io.homebird.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * HomebirdApiPropertiesTestO
 *
 * @author Anthony DePalma
 */
public class HomebirdApiPropertiesTest extends HomebirdApiTest {

	@Autowired
	private HomebirdApiProperties homebirdApiProperties;

	@Test
	public void homebirdApiProperties() {
		assertNotNull(homebirdApiProperties);
		assertNotNull(homebirdApiProperties.getJwtSecret());
	}

}
