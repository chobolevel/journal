package kr.co.space.diary.controller.exception;

import kr.co.space.diary.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(CustomException.class)
  public String error() {
    return "/exception/error";
  }

}
