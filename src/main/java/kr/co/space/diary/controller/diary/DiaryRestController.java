package kr.co.space.diary.controller.diary;

import kr.co.space.diary.entity.common.HttpResult;
import kr.co.space.diary.entity.diary.Diaries;
import kr.co.space.diary.entity.diary.Diary;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/diary")
@RequiredArgsConstructor
public class DiaryRestController {

  private final DiaryService diaryService;

  @GetMapping("{id}")
  public ResponseEntity<?> fetch(@PathVariable("id") String id) throws CustomException {
    return new ResponseEntity<>(diaryService.findOne(Diary.builder().id(id).build()), HttpStatus.OK);
  }

  @GetMapping("list")
  public ResponseEntity<?> search(Diaries diaries) throws CustomException {
    List<Diary> diaryList = diaryService.findAll(diaries);
    diaries.setDiaryList(diaryList);
    diaries.setTotalCnt(diaryService.findCount());
    return new ResponseEntity<>(diaries, HttpStatus.OK);
  }

  @PostMapping("write")
  public ResponseEntity<?> write(@RequestPart("diary") Diary diary, @RequestPart(value = "uploadFiles", required = false) List<MultipartFile> uploadFiles) throws CustomException, IOException {
    diaryService.create(diary, uploadFiles);
    return new ResponseEntity<>(HttpResult.create("success to create diary"), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> modify(@PathVariable("id") String id,
                                  @RequestPart("diary") Diary diary,
                                  @RequestPart(value = "uploadFiles", required = false) List<MultipartFile> uploadFiles) throws CustomException, IOException {
    diary.setId(id);
    diaryService.modify(diary, uploadFiles);
    return ResponseEntity.ok(HttpResult.ok("success to modify diary"));
  }

  @PutMapping("increase-view-cnt/{id}")
  public ResponseEntity<?> increaseViewCnt(HttpServletResponse res,
                                           @CookieValue(value = "viewedDiaryIds", required = false) String viewedDiaryIds,
                                           @PathVariable("id") String id) throws CustomException {
    if(viewedDiaryIds == null) {
      diaryService.increaseViewCnt(Diary.builder().id(id).build());
      Cookie cookie = new Cookie("viewedDiaryIds", id);
      cookie.setMaxAge(60*60*24);
      res.addCookie(cookie);
    } else {
      List<String> viewedDiaryIdList = Arrays.asList(viewedDiaryIds.split("/"));
      if(!viewedDiaryIdList.contains(id)) {
        // 조회수 상승 및 쿠키에 아이디 저장
        diaryService.increaseViewCnt(Diary.builder().id(id).build());
        Cookie cookie = new Cookie("viewedDiaryIds", viewedDiaryIds + "/" + id);
        cookie.setMaxAge(60*60*24);
        res.addCookie(cookie);
      }
    }
    return ResponseEntity.ok(HttpResult.ok("success to increase view count"));
  }

  @PutMapping("increase-like-cnt/{id}")
  public ResponseEntity<?> increaseLikeCnt(@PathVariable("id") String id) throws CustomException {
    diaryService.increaseLikeCnt(Diary.builder().id(id).build());
    return ResponseEntity.ok(HttpResult.ok("success to increase like count"));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> remove(@PathVariable("id") String id) throws CustomException {
    diaryService.remove(Diary.builder().id(id).build());
    return ResponseEntity.ok(HttpResult.ok("success to delete diary"));
  }

}
