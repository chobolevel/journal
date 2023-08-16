package kr.co.space.diary.controller.diary;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.attachment.Attachment;
import kr.co.space.diary.entity.diary.Diaries;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.attachment.AttachmentService;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/diary")
@RequiredArgsConstructor
public class DiaryRestController {

  private final DiaryService diaryService;
  private final AttachmentService attachmentService;
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${spring.servlet.multipart.location}")
  private String basePath;

  @GetMapping("{id}")
  public Diary fetch(@PathVariable("id") String id) throws CustomException {
    return diaryService.findOne(Diary.builder().id(id).build());
  }

  @GetMapping("list")
  public Diaries search(Diaries diaries) throws CustomException {
    List<Diary> diaryList = diaryService.findAll(diaries);
    diaries.setDiaryList(diaryList);
    diaries.setTotalCnt(diaryService.findCount());
    return diaries;
  }

  @PostMapping("write")
  public HttpStatus write(@RequestPart("diary") Diary diary, @RequestPart("uploadFiles") List<MultipartFile> uploadFiles) throws CustomException, IOException {
    String createdDiaryId = diaryService.create(diary);

    List<Attachment> attachmentList = uploadFiles.stream().map((file) -> new Attachment(createdDiaryId, file.getOriginalFilename())).toList();

    // 글 별로 폴더를 생성하고 해당 폴더로 분리해서 이미지 저장
    File folder = new File(basePath + "\\" + createdDiaryId);
    folder.mkdir();
    for(MultipartFile file : uploadFiles) {
      file.transferTo(new File(String.format("%s\\%s", createdDiaryId, file.getOriginalFilename())));
    }
    attachmentService.create(attachmentList);
    return HttpStatus.CREATED;
  }

  @PutMapping("{id}")
  public HttpStatus modify(@PathVariable("id") String id,
                                  @RequestPart("diary") Diary diary,
                                  @RequestPart(value = "uploadFiles", required = false) List<MultipartFile> uploadFiles) throws CustomException {
    diary.setId(id);
    diaryService.modify(diary);
    return HttpStatus.OK;
  }

  @PutMapping("increase-view-cnt/{id}")
  public HttpStatus increaseViewCnt(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("id") String id) throws CustomException {
    String memberId = principalDetails.getMember().getId();
    String viewedDiaryIds = redisTemplate.opsForValue().get(memberId);
    List<String> viewedDiaryIdList = new ArrayList<>();
    if(viewedDiaryIds == null) {
      // 없을 땐 새롭게 key:value 생성 -> 조회수 상승
      redisTemplate.opsForValue().set(memberId, id);
      diaryService.increaseViewCnt(Diary.builder().id(id).build());
    }
    else {
      // null이 아니면서 조회한 아이디에 포함되는 경우 아닌 경우 구분
      for(String s : viewedDiaryIds.split(", ")) {
        viewedDiaryIdList.add(s);
      }
      if(viewedDiaryIdList.contains(id)) {
        // 매칭되는 경우 -> 조회 수 상승 X
      } else {
        // 매칭되지 않는 경우
        viewedDiaryIdList.add(1, id);
        redisTemplate.opsForValue().set(memberId, String.join(", ", viewedDiaryIdList));
        diaryService.increaseViewCnt(Diary.builder().id(id).build());
      }
    }
    return HttpStatus.OK;
  }

  @PutMapping("increase-like-cnt/{id}")
  public HttpStatus increaseLikeCnt(@PathVariable("id") String id) throws CustomException {
    diaryService.increaseLikeCnt(Diary.builder().id(id).build());
    return HttpStatus.OK;
  }

  @DeleteMapping("{id}")
  public HttpStatus remove(@PathVariable("id") String id) throws CustomException {
    diaryService.remove(Diary.builder().id(id).build());
    return HttpStatus.OK;
  }

}
