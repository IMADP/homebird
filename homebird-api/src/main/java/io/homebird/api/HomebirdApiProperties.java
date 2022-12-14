package io.homebird.api;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * HomebirdApiProperties
 *
 * Global application property holder.
 *
 * @author Anthony DePalma
 */
@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "app")
public class HomebirdApiProperties {

	@NotNull
	private String appTitle;

	@NotNull
	private String appDescription;

	@NotNull
	private String appVersion;

	@NotNull
	private boolean emailSend;

	@NotNull
	private long jwtExpShort;

	@NotNull
	private long jwtExpLong;

	@NotNull
	private String jwtSecret;

	@NotNull
	private String corsAllowedOrigin;

}