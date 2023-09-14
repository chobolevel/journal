package kr.co.space.diary.entity.member;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Members {

  List<Member> memberList;
  private int pageNum;
  private int limit;
  private int offset;
  private int totalCnt;

  public Members(int pageNum, int limit) {
    this.pageNum = pageNum;
    this.limit = limit;
    this.offset = (pageNum - 1) * limit;
  }

}
