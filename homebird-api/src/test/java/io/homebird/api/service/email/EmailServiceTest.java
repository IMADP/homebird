package io.homebird.api.service.email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.homebird.api.HomebirdApiTest;
import io.homebird.api.service.user.User;

/**
 * EmailServiceTest
 * 
 * @author Anthony DePalma
 */
public class EmailServiceTest extends HomebirdApiTest {

	@Autowired
	private EmailService emailService;

	@Test
	public void nestedSubstituion() throws InterruptedException {
		User user = new User();
		user.setEmail("test@test.com");
		Email email = emailService.createEmail(EmailTemplate.VERIFY_USER, Map.of("user", user));
		assertEquals("test@test.com", email.getSubject());
	}

	@Test
	public void missingSubstituion() throws InterruptedException {
		Email email = emailService.createEmail(EmailTemplate.VERIFY_USER, Map.of("user", new User()));
		assertEquals("${user.email}", email.getSubject());
	}

}
