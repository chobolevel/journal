package kr.co.space.diary.enums.common;

import org.springframework.http.HttpStatus;

public enum CustomExceptionType {

  MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "#0[Type: #1]이/가 누락되었습니다."),
  FAIL_TO_FIND(HttpStatus.BAD_REQUEST, "요청하는 정보를 불러오지 못했습니다."),
  NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 일치하지 않습니다."),
  SAME_USERNAME_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");


  private final HttpStatus status;

  private final String message;

  CustomExceptionType(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

}
