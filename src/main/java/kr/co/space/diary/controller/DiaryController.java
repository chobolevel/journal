package kr.co.space.diary.controller;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.attachment.Attachment;
import kr.co.space.diary.entity.common.Criteria;
import kr.co.space.diary.entity.common.Paging;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.attachment.AttachmentService;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("diary")
@RequiredArgsConstructor
public class DiaryController {
  
  private final DiaryService diaryService;
  private final AttachmentService attachmentService;

  @Value("${spring.servlet.multipart.location}")
  private String basePath;

  @GetMapping("write")
  public String write(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
    model.addAttribute("member", principalDetails.getMember());
    return "/diary/write";
  }
  
  @PostMapping("write")
  public String write(Diary diary, @RequestParam("uploadFiles") List<MultipartFile> uploadFiles) throws CustomException, IOException {
    String createdDiaryId = diaryService.create(diary);

    List<Attachment> attachmentList = uploadFiles.stream().map((file) -> new Attachment(createdDiaryId, file.getOriginalFilename())).toList();

    // 글 별로 폴더를 생성하고 해당 폴더로 분리해서 이미지 저장
    File folder = new File(basePath + "\\" + createdDiaryId);
    folder.mkdir();
    for(MultipartFile file : uploadFiles) {
      file.transferTo(new File(String.format("%s\\%s", createdDiaryId, file.getOriginalFilename())));
    }
    attachmentService.create(attachmentList);
    return "redirect:/diary";
  }

  @GetMapping("")
  public String list(@AuthenticationPrincipal PrincipalDetails principalDetails, Criteria cri, Model model) {
    List<Diary> diaryList = diaryService.findAll(Diary.builder().writerId(principalDetails.getMember().getId()).build(), cri);
    model.addAttribute("list", diaryList);
    model.addAttribute("paging", new Paging(cri, diaryService.findCount()));
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
  public String modify(@PathVariable("id") String id, Diary diary, @RequestParam("uploadFiles") List<MultipartFile> uploadFiles) throws CustomException {
    diary.setId(id);
    diaryService.modify(diary);
    return "redirect:/diary";
  }

  @PutMapping("increase-view-cnt/{id}")
  public ResponseEntity<?> increaseViewCnt(@PathVariable("id") String id) throws CustomException {
    diaryService.increaseViewCnt(Diary.builder().id(id).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("increase-like-cnt/{id}")
  public ResponseEntity<?> increaseLikeCnt(@PathVariable("id") String id) throws CustomException {
    diaryService.increaseLikeCnt(Diary.builder().id(id).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("remove/{id}")
  public String remove(@PathVariable("id") String id) throws CustomException {
    diaryService.remove(Diary.builder().id(id).build());
    return "redirect:/diary";
  }

}
