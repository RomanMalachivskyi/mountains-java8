package com.home.education.mountains.common.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler({ LocationValidationFailedException.class })
	public ResponseEntity<Object> handleLocationValidation(LocationValidationFailedException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ RouteValidationFailedException.class })
	public ResponseEntity<Object> handleRouteValidation(RouteValidationFailedException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ CategoryValidationFailedException.class })
	public ResponseEntity<Object> handleCategoryValidation(CategoryValidationFailedException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ MountainValidationFailedException.class })
	public ResponseEntity<Object> handleMountainValidation(MountainValidationFailedException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstrainValidation(ConstraintViolationException ex) {
		log.error(ex.getMessage(), ex);
		StringBuilder result = new StringBuilder();
		ex.getConstraintViolations().forEach(d -> result.append(d.getMessage()));
		return new ResponseEntity<>(result, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleSqlExceptionHelper(DataIntegrityViolationException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMostSpecificCause().getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ LocationDoesNotExistsException.class })
	public ResponseEntity<Object> handleLocationDoesNotExists(LocationDoesNotExistsException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MountainDoesNotExistsException.class })
	public ResponseEntity<Object> handleMountainDoesNotExists(MountainDoesNotExistsException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ RouteDoesNotExistsException.class })
	public ResponseEntity<Object> handleRouteDoesNotExists(RouteDoesNotExistsException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ CategoryDoesNotExistsException.class })
	public ResponseEntity<Object> handleCategoryDoesNotExists(CategoryDoesNotExistsException ex) {
		log.error(ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> reasons = new ArrayList<>();

		BindingResult bindingResult = ex.getBindingResult();

		if (bindingResult != null) {
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				reasons.add(objectError.getDefaultMessage());
			}
		}

		return new ResponseEntity<Object>(reasons, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ IllegalStateException.class })
	public ResponseEntity<Object> handleRuntimeException(IllegalStateException ex) {
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
		log.error("Handling unexpected runtime exception: " + ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		log.error("Access is denied: " + ex.getMessage(), ex);
		return new ResponseEntity<>("Access is denied", HttpStatus.FORBIDDEN);
	}
	

}
