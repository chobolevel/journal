package kr.co.space.diary.service.diary;

import kr.co.space.diary.entity.diary.Diaries;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DiaryService {

  int findCount();

  List<Diary> findAll(Diaries diaries);

  Diary findOne(Diary diary) throws CustomException;

  void create(Diary diary, List<MultipartFile> uploadFiles) throws CustomException, IOException;

  void modify(Diary diary, List<MultipartFile> uploadFiles) throws CustomException, IOException;

  void increaseViewCnt(Diary diary) throws CustomException;

  void increaseLikeCnt(Diary diary) throws CustomException;

  void remove(Diary diary) throws CustomException;

}
