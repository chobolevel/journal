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
  public ResponseEntity<?> customException(CustomException exception) {
    CustomExceptionType type = exception.getType();
    if(type == CustomExceptionType.MISSING_PARAMETER) {
      return new ResponseEntity<>(CustomExceptionType.MISSING_PARAMETER, HttpStatus.BAD_REQUEST);
    } else if(type == CustomExceptionType.NOT_MATCH_PASSWORD) {
      return new ResponseEntity<>(CustomExceptionType.NOT_MATCH_PASSWORD, HttpStatus.BAD_REQUEST);
    } else if(type == CustomExceptionType.FAIL_TO_FIND) {
      return new ResponseEntity<>(CustomExceptionType.FAIL_TO_FIND, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(CustomExceptionType.FAIL_TO_FIND.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
