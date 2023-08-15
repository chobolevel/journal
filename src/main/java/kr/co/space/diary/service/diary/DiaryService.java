package kr.co.space.diary.service.diary;

import kr.co.space.diary.entity.diary.Diaries;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;

import java.util.List;

public interface DiaryService {

  int findCount();

  List<Diary> findAll(Diaries diaries);

  Diary findOne(Diary diary) throws CustomException;

  String create(Diary diary) throws CustomException;

  void modify(Diary diary) throws CustomException;

  void increaseViewCnt(Diary diary) throws CustomException;

  void increaseLikeCnt(Diary diary) throws CustomException;

  void remove(Diary diary) throws CustomException;

}
