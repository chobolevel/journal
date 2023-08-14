package kr.co.space.diary.controller.diary;

import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.attachment.AttachmentService;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String watch() {
    return "/diary/watch";
  }

  @GetMapping("modify/{id}")
  public String modify(@PathVariable("id") String id, Model model) throws CustomException {
    Diary findDiary = diaryService.findOne(Diary.builder().id(id).build());
    model.addAttribute("diary", findDiary);
    return "/diary/modify";
  }

}
