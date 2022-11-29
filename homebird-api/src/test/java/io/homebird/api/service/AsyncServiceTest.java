package io.homebird.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.homebird.api.HomebirdApiTest;

/**
 * AsyncServiceTest
 *
 * @author Anthony DePalma
 */
public class AsyncServiceTest extends HomebirdApiTest {

	@Autowired
	private AsyncService asyncService;

	@Test
	public void publicAsync() {
		asyncService.publicAsync();
	}

	@Test
	public void protectedAsync() {
		asyncService.protectedAsync();
	}

	@Test
	public void privateAsync() {
		asyncService.privateAsync();
	}

	@Test
	public void finalAsync() {
		asyncService.finalAsync();
	}

}
