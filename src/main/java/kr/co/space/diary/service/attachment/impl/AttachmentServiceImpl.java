package kr.co.space.diary.service.attachment.impl;

import kr.co.space.diary.entity.attachment.Attachment;
import kr.co.space.diary.mapper.attachment.AttachmentMapper;
import kr.co.space.diary.service.attachment.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

  private final AttachmentMapper attachmentMapper;

  @Override
  public void create(List<Attachment> attachmentList) {
    attachmentMapper.create(attachmentList);
  }

}
