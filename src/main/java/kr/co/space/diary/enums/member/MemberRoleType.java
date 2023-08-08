package kr.co.space.diary.enums.member;

import kr.co.space.diary.enums.EnumBase;

public enum MemberRoleType implements EnumBase {
  ROLE_USER("일반 사용자"),
  ROLE_ADMIN("일반 관리자");

  private final String name;

  MemberRoleType(String name) {
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
