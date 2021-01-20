package com.knu.ynortman.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime timestamp;
	   private int code;
	   private HttpStatus status;
	   private String message;

	   public ApiError(HttpStatus status, String message) {
	       timestamp = LocalDateTime.now();
	       this.status = status;
	       this.code = status.value();
	       this.message = message;
	   }
	}