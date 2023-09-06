package kr.co.space.diary.controller.diary;

import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.attachment.AttachmentService;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("diary")
@RequiredArgsConstructor
public class DiaryController {
  
  private final DiaryService diaryService;
  private final AttachmentService attachmentService;

  @Value("${spring.servlet.multipart.location}")
  private String basePath;

  @GetMapping("write")
  public String write() {
    return "/diary/write";
  }

  @GetMapping("list")
  public String list() {
    return "/diary/list";
  }

  @GetMapping("")
  public String modify() throws CustomException {
    return "/diary/watch";
  }

}
