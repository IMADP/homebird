package io.homebird.api.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.homebird.api.HomebirdApiTest;

/**
 * HttpUriTest
 *
 * @author Anthony DePalma
 */
public class HttpUriTest extends HomebirdApiTest {

	@Test
	public void uri() {
		String uri = "www.example.com/path";
		HttpUri httpUri = HttpUri.of(uri);
		assertEquals("www.example.com/path", httpUri.getUri());
	}

	@Test
	public void hostAndPath() {
		String host = "www.example.com";
		String path = "/path";
		HttpUri httpUri = HttpUri.of(host, path);
		assertEquals("www.example.com/path", httpUri.getUri());
	}

	@Test
	public void hostAndPathAndParam() {
		String host = "www.example.com";
		String path = "/path";
		Map<String, String> params = Map.of("sort", "asc");
		HttpUri httpUri = HttpUri.of(host, path, params);
		assertEquals("www.example.com/path?sort=asc", httpUri.getUri());
	}

}
