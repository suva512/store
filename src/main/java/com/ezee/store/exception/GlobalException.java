package com.ezee.store.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ezee.store.util.DateTimeUtil;
@ControllerAdvice
public class GlobalException {
//	@ExceptionHandler(ServiceException.class)
//	public ResponseEntity<?> handleServiceException(ServiceException exception) {		
//		ResponseException<ServiceException> responseException = new ResponseException<>();
//		responseException.setErrorCode(exception.getstatusCode());
//		responseException.setMessage(exception.getMessage());
//		responseException.setDescription(exception.getObject());
//		responseException.setResponseTime(DateTimeUtil.dateTimeFormat(LocalDateTime.now()));
//		
//		return ResponseEntity.ok(responseException);
//	}
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleInternalServerError(Exception exception) {
	        ResponseException<String> responseException = new ResponseException<>();
	        responseException.setErrorCode(200);
	        responseException.setMessage(exception.getMessage());
	        responseException.setResponseTime(DateTimeUtil.dateTimeFormat(LocalDateTime.now()));

	        return ResponseEntity.status(200).body(responseException);
	    }

}
