package com.ppol.alarm.exception.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ppol.alarm.exception.exception.EnumConvertException;
import com.ppol.alarm.exception.exception.ForbiddenException;
import com.ppol.alarm.util.response.ResponseBuilder;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * 서버에서 발생하는 에러들을 처리하기 위한 핸들러
 * RestControllerAdvice를 통해 에러에 대한 Response를 클라이언트로 전달한다.
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	/**
	 * {@link EnumConvertException} Enum 변환 시 발생하는 에러들을 처리한다.
	 * 서버 내부 에러이므로 500 코드를 반환한다.
	 */
	@ExceptionHandler({EnumConvertException.class})
	public ResponseEntity<?> handleEnumConvertException(EnumConvertException exception) {

		log.error("ENUM 변환 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("서버 내부 에러입니다.\n관리자에게 문의해주세요.");
	}

	/**
	 * {@link EntityNotFoundException} repository에서 Id값 혹은 다른 값을 통해 Entity를 찾을 때 없다면 발생하는 에러
	 * 클라이언트로 부터 받은 parameter가 잘못되어 발생했기 때문에 400 코드를 반환한다.
	 */
	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {

		log.error("Entity not found 에러");
		exception.printStackTrace();

		return ResponseBuilder.badRequest("존재하지 않는 " + exception.getMessage() + "입니다.");
	}

	/**
	 * {@link ForbiddenException} 사용자가 접근 권한이 없는 서비스에 접근할 때 발생하는 에러
	 * 권한 관련 에러이므로 403 코드를 반환한다.
	 */
	@ExceptionHandler({ForbiddenException.class})
	public ResponseEntity<?> handleForbiddenException(ForbiddenException exception) {

		log.error("권한 에러");
		exception.printStackTrace();

		return ResponseBuilder.forbidden("해당 " + exception.getMsg() + "에 대한 접근 권한이 없습니다.");
	}

	/**
	 * {@link MethodArgumentNotValidException} spring validation 처리 중 발생하는 에러들을 처리하기 위함
	 * 클라이언트로 부터 받은 데이터에서 유효성 검사에 실패하여 발생했기 때문에 400 코드를 반환한다.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
		// 유효성 검사 오류를 가져온다.
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		// 오류 메시지를 구성한다.
		StringBuilder errorMsg = new StringBuilder();
		for (FieldError error : fieldErrors) {
			errorMsg.append(error.getDefaultMessage());
		}

		exception.printStackTrace();

		return ResponseBuilder.badRequest("잘못된 형식입니다. (" + errorMsg + ")");
	}

	/**
	 * 이 외에 따로 잡아내지 못한 서버 내부에러들을 처리하기 위함
	 * 내부 에러이므로 500 코드를 반환한다.
	 */
	@ExceptionHandler({Exception.class})
	public ResponseEntity<?> handleOtherException(Exception exception) {

		log.error("서버 내부 에러");
		exception.printStackTrace();

		return ResponseBuilder.internalServerError("서버 내부 에러입니다.\n관리자에게 문의해주세요.");
	}
}
