package io.homebird.api.http;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import io.homebird.api.HomebirdApiTest;

/**
 * HttpResponseTest
 *
 * @author Anthony DePalma
 */
public class HttpResponsesTest extends HomebirdApiTest {

	@Test
	public void assertStatus() {
		HttpUri uri = HttpUri.of("www.example.com");
		HttpMethod method = HttpMethod.GET;
		HttpHeaders headers = HttpHeaders.NONE;
		HttpContent content = HttpContent.ofText("text");
		HttpStatus status = HttpStatus.OK;

		HttpRequest request = HttpRequest.get(uri,  headers);
		HttpResponse response = HttpResponse.of(uri, method,  headers,  content,  status);

		response.assertStatus(request, HttpStatus.OK);

		assertThrows(HttpException.class, () -> {
			response.assertStatus(request, HttpStatus.INTERNAL_SERVER_ERROR);
		});
	}

}
