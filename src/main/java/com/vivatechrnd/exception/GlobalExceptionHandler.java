package com.vivatechrnd.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	//endpoint not found exception
		@ExceptionHandler(NoHandlerFoundException.class)
		public ResponseEntity<MyErrorDetail> notFoundExceptionHandler(NoHandlerFoundException nhfe
				, WebRequest webRequest){
			MyErrorDetail myErrorDetail = new MyErrorDetail();
			
			myErrorDetail.setLocalDateTime(LocalDateTime.now());
			myErrorDetail.setDetail(webRequest.getDescription(false));
			myErrorDetail.setMessage(nhfe.getMessage());
			
			return new ResponseEntity<MyErrorDetail>(myErrorDetail, HttpStatus.BAD_REQUEST);
		}
		
		//validation failed exception
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<MyErrorDetail> validationExceptionHandler(MethodArgumentNotValidException manve){
			MyErrorDetail myErrorDetail = new MyErrorDetail();
			
			myErrorDetail.setLocalDateTime(LocalDateTime.now());
			myErrorDetail.setDetail(manve.getBindingResult().getFieldError().getDefaultMessage());
			myErrorDetail.setMessage("Validation error");
			
			return new ResponseEntity<MyErrorDetail>(myErrorDetail, HttpStatus.BAD_GATEWAY);
		}
		
		@ExceptionHandler(UserException.class)
		public ResponseEntity<MyErrorDetail> userExceptionHandler(UserException userException,
				WebRequest webRequest){
			MyErrorDetail myError = new MyErrorDetail(userException.getMessage(), 
					webRequest.getDescription(false), LocalDateTime.now());
			
			return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(RoleException.class)
		public ResponseEntity<MyErrorDetail> roleExceptionHandler(RoleException roleException,
				WebRequest webRequest){
			MyErrorDetail myError = new MyErrorDetail(roleException.getMessage(), 
					webRequest.getDescription(false), LocalDateTime.now());
			
			return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(Exception.class)
		public ResponseEntity<MyErrorDetail> allExceptionHandler(Exception exception, WebRequest webRequest){
			MyErrorDetail myError = new MyErrorDetail();
			
			myError.setLocalDateTime(LocalDateTime.now());
			myError.setMessage(exception.getMessage());
			myError.setDetail(webRequest.getDescription(false));
			
			return new ResponseEntity<MyErrorDetail>(myError, HttpStatus.BAD_REQUEST);
		}
}
