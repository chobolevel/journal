package kr.co.space.diary.service.diary.impl;

import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.mapper.diary.DiaryMapper;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

  private final DiaryMapper diaryMapper;

  @Override
  public List<Diary> findAll(Diary diary) {
    return diaryMapper.findAll(diary);
  }

  @Override
  public Diary findOne(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    return diaryMapper.findOne(diary);
  }

  @Override
  public void create(Diary diary) throws CustomException {
    if(diary.getTitle() == null || diary.getTitle().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "title");
    }
    else if(diary.getContent() == null || diary.getContent().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "content");
    }
    else if(diary.getWriter() == null || diary.getWriter().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "writer");
    }
    diaryMapper.create(diary);
  }

  @Override
  public void modify(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    diaryMapper.modify(diary);
  }

  @Override
  public void remove(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    diaryMapper.remove(diary);
  }
}
