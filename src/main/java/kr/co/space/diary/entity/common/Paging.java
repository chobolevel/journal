package kr.co.space.diary.entity.common;

import lombok.Getter;

@Getter
public class Paging {

  private int startPage;
  private int endPage;
  private boolean prev, next;
  private int totalCnt;

  private Criteria cri;

  public Paging(Criteria cri, int totalCnt) {
    this.cri = cri;
    this.totalCnt = totalCnt;

    this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
    this.startPage = this.endPage - 9;

    int realEnd = (int)(Math.ceil((totalCnt * 1.0) / cri.getLimit()));

    if(realEnd < this.endPage) {
      this.endPage = realEnd;
    }

    this.prev = this.startPage > 1;
    this.next = this.endPage < realEnd;
  }

}
