package com.ppol.article.exception.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ppol.article.exception.exception.EnumConvertException;
import com.ppol.article.exception.exception.ImageCountException;
import com.ppol.article.exception.exception.S3Exception;
import com.ppol.article.util.response.ResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler({EnumConvertException.class})
	public ResponseEntity<?> handleEnumConvertException(EnumConvertException exception) {

		log.error("ENUM 변환 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("서버 내부 에러입니다.\n관리자에게 문의해주세요.");
	}

	@ExceptionHandler({S3Exception.class})
	public ResponseEntity<?> handleS3Exception(S3Exception exception) {

		log.error("S3 server 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("파일 서버 에러입니다.\n관리자에게 문의해주세요.");
	}

	@ExceptionHandler({ImageCountException.class})
	public ResponseEntity<?> handleImageCountException(ImageCountException exception) {

		log.error("게시글 이미지 개수 에러");
		exception.printStackTrace();

		return ResponseBuilder.badRequest("게시글에는 최대 10개의 사진을 등록할 수 있습니다.");
	}

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {

		log.error("Entity not found 에러");
		exception.printStackTrace();

		return ResponseBuilder.badRequest("존재하지 않는 " + exception.getMessage() + "입니다.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
		// 유효성 검사 오류를 가져옵니다.
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		// 오류 메시지를 구성합니다.
		StringBuilder errorMsg = new StringBuilder();
		for (FieldError error : fieldErrors) {
			errorMsg.append(error.getDefaultMessage());
		}

		exception.printStackTrace();

		return ResponseBuilder.badRequest("잘못된 형식입니다. (" + errorMsg + ")");
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<?> handleOtherException(Exception exception) {

		log.error("서버 내부 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("서버 내부 에러입니다.\n관리자에게 문의해주세요.");
	}
}
