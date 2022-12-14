package io.homebird.api;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.homebird.api.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * HomebirdApiAdvice
 *
 * Global exception handler which maps exceptions to http responses.
 *
 * @author Anthony DePalma
 */
@Slf4j
@ControllerAdvice
public class HomebirdApiAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException ex, HttpServletRequest request) {
		log.error(String.format("Error processing [%s]", request.getRequestURI()), ex);
		return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
		log.error(String.format("Error processing [%s]", request.getRequestURI()), ex);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
		log.error(String.format("Error processing [%s]", request.getRequestURI()), ex);
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<?> handleOptimisticLockingFailureException(OptimisticLockingFailureException ex, HttpServletRequest request) {
		log.error(String.format("Error processing [%s]", request.getRequestURI()), ex);
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
		log.error(String.format("Error processing [%s]", request.getRequestURI()), ex);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}