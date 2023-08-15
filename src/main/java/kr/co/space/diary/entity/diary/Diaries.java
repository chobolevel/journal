package kr.co.space.diary.entity.diary;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Diaries {

  List<Diary> diaryList;
  private int pageNum;
  private int limit;
  private int offset;
  private int totalCnt;

  public Diaries(int pageNum, int limit) {
    this.pageNum = pageNum;
    this.limit = limit;
    this.offset = (pageNum - 1) * limit;
  }

}
