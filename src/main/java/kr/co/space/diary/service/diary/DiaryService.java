package kr.co.space.diary.service.diary;

import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;

import java.util.List;

public interface DiaryService {

  List<Diary> findAll(Diary diary);

  Diary findOne(Diary diary) throws CustomException;

  void create(Diary diary) throws CustomException;

  void modify(Diary diary) throws CustomException;

  void remove(Diary diary) throws CustomException;

}
