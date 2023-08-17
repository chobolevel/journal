package kr.co.space.diary.enums.member;

import kr.co.space.diary.enums.EnumBase;

public enum MemberResignType implements EnumBase {
  RESIGN("y"),
  NOT_RESIGN("n")
  ;

  private final String name;

  MemberResignType(String name) {
    this.name = name;
  }

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public String getName() {
    return name;
  }
}
