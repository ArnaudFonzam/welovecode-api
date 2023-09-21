package com.wlovec.welovecodeapi.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wlovec.welovecodeapi.dto.ExceptionMessage;
import com.wlovec.welovecodeapi.dto.FormationException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

	 private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    @ExceptionHandler(NullPointerException.class)
	    public ResponseEntity<ExceptionMessage> nullPointerExceptionHandler(HttpServletRequest request, NullPointerException exception) {
	        ExceptionMessage message = ExceptionMessage.builder()
	            .date(LocalDateTime.now().format(formatter))
	            .path(request.getRequestURI().toString() + "?" + request.getQueryString())
	            .className(exception.getClass().getName())
	            .message("Tu veux éviter les null ? N'hésite pas à lire cet article: https://www.developpez.net/forums/blogs/473169-gugelhupf/b2944/java-astuces-eviter-nullpointerexception/")
	            .build();
	        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    @ExceptionHandler(FormationException.class)
	    public ResponseEntity<ExceptionMessage> wrongAddressExceptionHandler(HttpServletRequest request, FormationException exception) {
	        ExceptionMessage message = ExceptionMessage.builder()
	            .date(LocalDateTime.now().format(formatter))
	            .path(request.getRequestURI().toString() + "?" + request.getQueryString())
	            .className(exception.getClass().getName())
	            .message(exception.getMessage())
	            .build();
	        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ExceptionMessage> genericExceptionHandler(HttpServletRequest request, Exception exception) {
	        ExceptionMessage message = ExceptionMessage.builder()
	            .date(LocalDateTime.now().format(formatter))
	            .path(request.getRequestURI().toString() + "?" + request.getQueryString())
	            .className(exception.getClass().getName())
	            .message(exception.getMessage())
	            .build();
	        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
