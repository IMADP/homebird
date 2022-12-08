package io.homebird.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.net.HttpHeaders;

import io.homebird.api.service.user.UserClaim;
import io.homebird.api.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;

/**
 * HomebirdApiSecurityFilter
 *
 * Security filter that extracts a JWT from the Authorization header and sets it into the SecurityContext.
 * Additionally, the filter will renew the token to extend the expiration, simulating the behavior of traditional stateful sessions.
 *
 * @author Anthony DePalma
 */
@Component
@RequiredArgsConstructor
public class HomebirdApiSecurityFilter extends OncePerRequestFilter {

	// the authorization prefix
	private static final String AUTH_PREFIX = "Bearer ";

	// properties
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		// continue along the filter chain if no bearer token was provided
		if (authorization == null || !authorization.startsWith(AUTH_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		try
		{
			// parse the jwt token
			String token = authorization.substring(AUTH_PREFIX.length());
			Jws<Claims> jws = userService.parseToken(token);

			// validate the jwt token
			Optional<Boolean> valid = userService.validateToken(jws);

			// if the token didn't pass validation, throw an exception
			if(valid.isPresent() && !valid.get().booleanValue()) {
				throw new IllegalArgumentException("Invalid token");
			}

			// store the authentication details into the security context
			UserClaim claim = jws.getBody().get(UserClaim.KEY, UserClaim.class);
			List<GrantedAuthority> authorities = Arrays.asList(claim.getAuthority());
			Authentication authentication = new UsernamePasswordAuthenticationToken(claim, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// update the token to extend the expiration
			String newToken = userService.renewToken(jws, valid.isPresent() && valid.get().booleanValue());
			response.addHeader(HttpHeaders.AUTHORIZATION, AUTH_PREFIX + newToken);

			// continue along the filter chain
			chain.doFilter(request, response);
		}
		catch(Exception exception)
		{
			// exceptions from invalid tokens correspond to status code 401 UNAUTHORIZED
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.addHeader(HttpHeaders.WWW_AUTHENTICATE, AUTH_PREFIX.trim());
		}

	}

}