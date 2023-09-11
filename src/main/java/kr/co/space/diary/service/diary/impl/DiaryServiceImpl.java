package kr.co.space.diary.service.diary.impl;

import kr.co.space.diary.entity.attachment.Attachment;
import kr.co.space.diary.entity.diary.Diaries;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.mapper.attachment.AttachmentMapper;
import kr.co.space.diary.mapper.diary.DiaryMapper;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

  private final DiaryMapper diaryMapper;
  private final AttachmentMapper attachmentMapper;
  @Value("${spring.servlet.multipart.location}")
  private String basePath;

  @Override
  public int findCount() {
    return diaryMapper.findCount();
  }

  @Override
  public List<Diary> findAll(Diaries diaries) {
    return diaryMapper.findAll(diaries);
  }

  @Override
  public Diary findOne(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    return diaryMapper.findOne(diary);
  }

  @Override
  public void create(Diary diary, List<MultipartFile> uploadFiles) throws CustomException, IOException {
    if(diary.getTitle() == null || diary.getTitle().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "title");
    }
    else if(diary.getContent() == null || diary.getContent().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "content");
    }
    else if(diary.getWriterId() == null || diary.getWriterId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "writer");
    }
    String diaryId = UUID.randomUUID().toString();
    diary.setId(diaryId);

    if(uploadFiles != null) {
      List<Attachment> list = uploadFiles.stream().map((file) -> new Attachment(diaryId, file.getOriginalFilename())).toList();
      // 글 별로 폴더를 생성하고 해당 폴더로 분리해서 이미지 저장
      File folder = new File(basePath + "\\" + diaryId);
      folder.mkdir();
      for(MultipartFile file : uploadFiles) {
        file.transferTo(new File(String.format("%s\\%s", diaryId, file.getOriginalFilename())));
      }
      attachmentMapper.create(list);
    }

    diaryMapper.create(diary);
  }

  @Override
  public void modify(Diary diary, List<MultipartFile> uploadFiles) throws CustomException, IOException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }

    if(uploadFiles != null) {
      String diaryId = diary.getId();
      List<Attachment> list = uploadFiles.stream().map((file) -> new Attachment(diaryId, file.getOriginalFilename())).toList();
      File folder = new File(basePath + "\\" + diaryId);
      if(folder.exists()) {
        // 경로가 존재하는 경우 패키지 삭제하고 새롭게 생성
        attachmentMapper.deleteByDiaryId(Diary.builder().id(diaryId).build());
        File[] files = folder.listFiles();
        for(File file : files) {
          file.delete();
        }
        folder.delete();
        folder.mkdir();
      } else {
        // 경로가 존재하지 않는 경우
        folder.mkdir();
      }
      for(MultipartFile file : uploadFiles) {
        file.transferTo(new File(String.format("%s\\%s", diaryId, file.getOriginalFilename())));
      }
      attachmentMapper.create(list);
    }

    diaryMapper.modify(diary);
  }

  @Override
  public void increaseViewCnt(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    diaryMapper.increaseViewCnt(diary);
  }

  @Override
  public void increaseLikeCnt(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    diaryMapper.increaseLikeCnt(diary);
  }

  @Override
  public void remove(Diary diary) throws CustomException {
    if(diary.getId() == null || diary.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    attachmentMapper.deleteByDiaryId(diary);
    diaryMapper.remove(diary);
  }
}
