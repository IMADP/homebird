package io.homebird.api.service.user;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.homebird.api.HomebirdApiProperties;
import io.homebird.api.service.email.Email;
import io.homebird.api.service.email.EmailService;
import io.homebird.api.service.email.EmailTemplate;
import io.homebird.api.validation.ValidationException;
import io.homebird.api.validation.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserService
 *
 * @author Anthony DePalma
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	// properties
	private final EmailService emailService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Validator<Object> validator;
	private final UserRequestValidator userRequestValidator;
	private final HomebirdApiProperties properties;

	/**
	 * Gets a user by id.
	 *
	 * @param id
	 * @return User
	 * @throws EntityNotFoundException
	 */
	public User getById(UUID id) throws EntityNotFoundException {
		return userRepository.getById(id);
	}

	/**
	 * Creates a user with the given authority.
	 *
	 * @param request
	 * @param authority
	 * @return User
	 */
	@Transactional
	public User createUser(UserRequest request, UserAuthority authority) {
		log.debug("Creating user [{}]", request.getEmail());

		// validate the request
		userRequestValidator.validate(request);

		// create the user
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPasswordDate(Instant.now());
		user.setVerifyToken(UUID.randomUUID());
		user.setResetToken(UUID.randomUUID());
		user.setAuthority(authority);
		user.setEnabled(true);
		user.setVerified(false);
		return saveUser(user);
	}

	/**
	 * Sends a verification email to the given user.
	 *
	 * @param userId
	 */
	@Transactional
	public void sendVerifyEmail(UUID userId) {
		log.debug("Sending verify email for user [{}]", userId);
		User user = userRepository.getById(userId);

		if(user.isVerified())
			throw ValidationException.of("verified");

		// update email token
		user.setVerifyToken(UUID.randomUUID());
		user = saveUser(user);

		// send the email
		Map<String, Object> context = Map.of("user", user);
		Email email = emailService.createEmail(EmailTemplate.VERIFY_USER, context);
		email.setToAddress(user.getEmail());
		emailService.sendEmail(email);
	}

	/**
	 * Verifies a user with the given token.
	 *
	 * @param verifyToken
	 * @return user
	 */
	@Transactional
	public User verifyUser(UserVerifyRequest request) {
		log.debug("Verify user");

		// validate the request
		validator.validate(request);

		// verify the user
		User user = userRepository.getByVerifyToken(request.getToken());
		user.setVerifyToken(UUID.randomUUID());
		user.setVerified(true);
		return saveUser(user);
	}

	/**
	 * Updates a user's email address
	 *
	 * @param userId
	 * @param request
	 * @return User
	 */
	@Transactional
	public User updateEmail(UUID id, UserEmailRequest request) {
		log.debug("Updating email for user [{}]", id);

		// validate the request
		validator.validate(request);

		// update the user
		User user = userRepository.getById(id);
		user.setEmail(request.getEmail());
		user.setVerifyToken(UUID.randomUUID());
		user.setVerified(false);
		return saveUser(user);
	}

	/**
	 * Sends a password reset email to the given user.
	 *
	 * @param userId
	 */
	@Transactional
	public void sendResetEmail(UUID id) {
		log.debug("Sending reset email for user [{}]", id);
		User user = userRepository.getById(id);

		// update reset token
		user.setResetToken(UUID.randomUUID());
		user = saveUser(user);

		// send the email
		Map<String, Object> context = Map.of("user", user);
		Email email = emailService.createEmail(EmailTemplate.RESET_USER, context);
		email.setToAddress(user.getEmail());
		emailService.sendEmail(email);
	}

	/**
	 * Resets a user's password.
	 *
	 * @param verifyToken
	 */
	@Transactional
	public User resetUser(UserResetRequest request) {
		log.debug("Reset user");

		// validate the request
		validator.validate(request);

		// reset the password;
		User user = userRepository.getByResetToken(request.getToken());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPasswordDate(Instant.now());
		user.setResetToken(UUID.randomUUID());
		return saveUser(user);
	}

	/**
	 * Updates a user's password
	 *
	 * @param userId
	 * @param request
	 * @return User
	 */
	@Transactional
	public User updatePassword(UUID id, UserPasswordRequest request) {
		log.debug("Updating password for user [{}]", id);

		// validate the request
		validator.validate(request);

		// update the user
		User user = userRepository.getById(id);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPasswordDate(Instant.now());
		return saveUser(user);
	}

	/**
	 * Returns a list of all users.
	 *
	 * @return List<User>
	 */
	public List<User> findUsers() {
		log.debug("Finding all users");
		return userRepository.select().fetch();
	}

	/**
	 * Validates and saves a user.
	 *
	 * @param user
	 * @return User
	 */
	private User saveUser(User user) {
		log.debug("Saving user [{}]", user.getId());
		validator.validate(user);
		return userRepository.save(user);
	}

	/**
	 * Returns an authentication token if the request was valid.
	 *
	 * @param request
	 * @return UserTokenResponse
	 */
	public UserTokenResponse getToken(UserTokenRequest request) {

		// validate the request
		validator.validate(request);

		// authenticate the user
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

		// validate the authentication attempt
		if(optionalUser.isEmpty()) {
			throw ValidationException.of("email", "notFound");
		}

		if(!passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
			throw ValidationException.of("password", "notPassword");
		}

		if(!optionalUser.get().isEnabled()) {
			throw ValidationException.of("notEnabled");
		}

		User user = optionalUser.get();

		// create a new token
		String token = createToken(user, request.isLongExpire());

		// return the auth response
		UserTokenResponse response = new UserTokenResponse();
		response.setUser(new UserResponse(user));
		response.setToken(token);
		return response;
	}

	/**
	 * Creates a new token containing:
	 *
	 * <code>iat:</code> The time the token was issued
	 * <code>exp:</code> The expiration time, with either a long or short duration
	 * <code>clm:</code> UserClaim object holding user claim details
	 *
	 * @param user
	 * @param longExpire
	 * @return String
	 */
	private String createToken(User user, boolean longExpire) {
		Instant now = Instant.now();
		Instant expiration = now.plusSeconds(longExpire ? properties.getJwtExpLong() : properties.getJwtExpShort());
		UserClaim claim = new UserClaim(user, longExpire);

		return Jwts.builder()
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiration))
				.claim(UserClaim.KEY, claim)
				.signWith(getKey())
				.compact();
	}

	/**
	 * Creates a new token from the given jwt, and optionally resets the issuedAt date.
	 *
	 * @param jws
	 * @param renewIssuedAt
	 * @return String
	 */
	public String renewToken(Jws<Claims> jws, boolean renewIssuedAt) {
		UserClaim claim = jws.getBody().get(UserClaim.KEY, UserClaim.class);
		Instant expiration = Instant.now().plusSeconds(claim.isLongExpiration() ? properties.getJwtExpLong() : properties.getJwtExpShort());
		Date issuedAt = renewIssuedAt ? Date.from(Instant.now()) : jws.getBody().getIssuedAt();

		return Jwts.builder()
				.setIssuedAt(issuedAt)
				.setExpiration(Date.from(expiration))
				.claim(UserClaim.KEY, claim)
				.signWith(getKey())
				.compact();
	}

	/**
	 * Validates a token by checking the user against the database.
	 * This is not done on every request, but rather during regular short expiration intervals.
	 *
	 * @param jws
	 * @return Optional<Boolean> which will be empty if validation was not performed
	 */
	public Optional<Boolean> validateToken(Jws<Claims> jws) {
		Instant issuedAt = jws.getBody().getIssuedAt().toInstant();
		Instant validateOn = issuedAt.plusSeconds(properties.getJwtExpShort());

		// if the validation deadline is still in the future, return an empty optional
		if(validateOn.isAfter(Instant.now())) {
			return Optional.empty();
		}

		// otherwise ensure that the user in the database is valid
		UserClaim claim = jws.getBody().get(UserClaim.KEY, UserClaim.class);
		User user = getById(claim.getUserId());

		// if the claim doesn't match the user details, return false
		if(!claim.isMatch(user)) {
			return Optional.of(Boolean.FALSE);
		}

		// return true if the user is enabled
		return Optional.of(user.isEnabled());
	}

	/**
	 * Parses a token.
	 *
	 * @param token
	 * @throws ExpiredJwtException
	 * @throws UnsupportedJwtException
	 * @throws MalformedJwtException
	 * @throws SignatureException
	 * @throws IllegalArgumentException
	 * @return Jws<Claims>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Jws<Claims> parseToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
		Map<String, Class<UserClaim>> map = Collections.singletonMap(UserClaim.KEY, UserClaim.class);

		return Jwts.parserBuilder()
				.deserializeJsonWith(new JacksonDeserializer(map))
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token);
	}

	/**
	 * Creates the HMAC key using the jwt secret.
	 *
	 * @return Key
	 */
	private Key getKey() {
		byte[] secret = Base64.getDecoder().decode(properties.getJwtSecret());
		return Keys.hmacShaKeyFor(secret);
	}

}