package kr.co.space.diary.mapper.attachment;

import kr.co.space.diary.entity.attachment.Attachment;
import kr.co.space.diary.entity.diary.Diary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentMapper {

  void create(List<Attachment> attachmentList);

  void deleteByDiaryId(Diary diary);

}
