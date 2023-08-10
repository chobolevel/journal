package kr.co.space.diary.entity.common;

import lombok.Getter;

@Getter
public class Criteria {

  private int pageNum;
  private int limit;
  private int offset;

  public Criteria(int pageNum, int limit) {
    this.pageNum = pageNum;
    this.limit = limit;
    this.offset = (pageNum - 1) * limit;
  }

}
