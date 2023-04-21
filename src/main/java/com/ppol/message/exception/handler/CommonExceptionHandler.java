package com.ppol.message.exception.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ppol.message.util.response.ResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {

		log.error("Entity not found 에러");
		exception.printStackTrace();

		return ResponseBuilder.badRequest("존재하지 않는 " + exception.getMessage() + "입니다.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
		// 유효성 검사 오류를 가져옵니다.
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		// 오류 메시지를 구성합니다.
		StringBuilder errorMsg = new StringBuilder();
		for (FieldError error : fieldErrors) {
			errorMsg.append(error.getDefaultMessage());
		}

		log.error(errorMsg.toString());
		exception.printStackTrace();

		return null;
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<?> handleOtherException(Exception exception) {

		log.error("서버 내부 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("서버 내부 에러입니다. (알수 없음)");
	}
}
