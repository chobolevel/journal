package kr.co.space.diary.controller;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("diary")
@RequiredArgsConstructor
public class DiaryController {
  
  private final DiaryService diaryService;

  @GetMapping("write")
  public String write(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
    model.addAttribute("member", principalDetails.getMember());
    return "/diary/write";
  }
  
  @PostMapping("write")
  public String write(Diary diary) throws CustomException {
    diaryService.create(diary);
    // 추후 페이징 완료되면 글 목록으로 이동 처리
    return "redirect:/diary";
  }

  @GetMapping("")
  public String list(@AuthenticationPrincipal PrincipalDetails principalDetails,  Model model) {
    List<Diary> diaryList = diaryService.findAll(Diary.builder().writerId(principalDetails.getMember().getId()).build());
    model.addAttribute("list", diaryList);
    return "/diary/list";
  }

  @GetMapping("{id}")
  public String watch(@PathVariable("id") String id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) throws CustomException {
    Diary findDiary = diaryService.findOne(Diary.builder().id(id).build());
    model.addAttribute("member", principalDetails.getMember());
    model.addAttribute("diary", findDiary);
    return "/diary/watch";
  }

  @GetMapping("modify/{id}")
  public String modify(@PathVariable("id") String id, Model model) throws CustomException {
    Diary findDiary = diaryService.findOne(Diary.builder().id(id).build());
    model.addAttribute("diary", findDiary);
    return "/diary/modify";
  }

  @PutMapping("modify/{id}")
  public String modify(@PathVariable("id") String id, Diary diary) throws CustomException {
    diary.setId(id);
    diaryService.modify(diary);
    return "redirect:/diary";
  }

  @DeleteMapping("remove/{id}")
  public String remove(@PathVariable("id") String id) throws CustomException {
    diaryService.remove(Diary.builder().id(id).build());
    return "redirect:/diary";
  }

}
