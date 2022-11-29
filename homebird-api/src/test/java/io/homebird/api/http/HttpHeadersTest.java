package io.homebird.api.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.homebird.api.HomebirdApiTest;

/**
 * HttpHeadersTest
 *
 * @author Anthony DePalma
 */
public class HttpHeadersTest extends HomebirdApiTest {

	@Test
	public void of() {
		HttpHeaders httpHeaders = HttpHeaders.of(Map.of("key", "value"));
		assertEquals("value", httpHeaders.getHeader("key"));
	}

}
