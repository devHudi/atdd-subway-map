package wooteco.subway.admin.controller.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wooteco.subway.admin.controller.exception.*;
import wooteco.subway.admin.dto.ExceptionResponse;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class SubwayControllerAdvice {
	private static final Logger LOGGER = LogManager.getLogger("SubwayControllerAdvice");

	@ExceptionHandler({
			NoLineExistException.class,
			InvalidLineFieldException.class,
			NoStationExistException.class,
			InvalidStationFieldException.class,
			LineStationCreateException.class
	})
	public ResponseEntity<ExceptionResponse> getEntityLogicException(RuntimeException e) {
		LOGGER.error(e);
		return new ResponseEntity<>(ExceptionResponse.of(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ExceptionResponse> getDuplicateKeyException(DuplicateKeyException e) {
		LOGGER.error(e);
		return new ResponseEntity<>(ExceptionResponse.of("입력값이 중복됩니다."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<ExceptionResponse> getDateTimeParseException(DateTimeParseException e) {
		LOGGER.error(e);
		return new ResponseEntity<>(ExceptionResponse.of("시간은 00:00:00 형태로 입력하셔야합니다."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> getMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		LOGGER.error(e.getMessage());
		return new ResponseEntity<>(ExceptionResponse.of("모든 정보가 입력되지 않았습니다."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DbActionExecutionException.class)
	public ResponseEntity<ExceptionResponse> getDbActionExecutionException(DbActionExecutionException e) {
		LOGGER.error(e.getMessage());
		return new ResponseEntity<>(ExceptionResponse.of("데이터베이스 문제 발생"), HttpStatus.BAD_REQUEST);
	}
}