package kr.co.space.diary.controller.exception;

import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestController {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> customException() {
    return new ResponseEntity<>(CustomExceptionType.FAIL_TO_FIND.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
