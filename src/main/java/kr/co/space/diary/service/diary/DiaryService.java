package kr.co.space.diary.service.diary;

import kr.co.space.diary.entity.common.Criteria;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;

import java.util.List;

public interface DiaryService {

  int findCount();

  List<Diary> findAll(Diary diary, Criteria cri);

  Diary findOne(Diary diary) throws CustomException;

  String create(Diary diary) throws CustomException;

  void modify(Diary diary) throws CustomException;

  void increaseViewCnt(Diary diary) throws CustomException;

  void increaseLikeCnt(Diary diary) throws CustomException;

  void remove(Diary diary) throws CustomException;

}
