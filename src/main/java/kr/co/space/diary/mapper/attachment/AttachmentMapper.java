package kr.co.space.diary.mapper.attachment;

import kr.co.space.diary.entity.attachment.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachmentMapper {

  void create(List<Attachment> attachmentList);

}
