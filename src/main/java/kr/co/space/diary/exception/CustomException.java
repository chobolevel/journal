package kr.co.space.diary.exception;

import kr.co.space.diary.enums.common.CustomExceptionType;

public class CustomException extends Exception{

  private final CustomExceptionType type;

  private String[] args;

  public CustomException(CustomExceptionType type) {
    super(type.getMessage());
    this.type = type;
  }

  public CustomException(CustomExceptionType type, String... args) {
    super(type.getMessage());
    this.type = type;
    this.args = args;
  }

  public CustomExceptionType getType() {
    return type;
  }

  public String[] getArgs() {
    return args;
  }

}
