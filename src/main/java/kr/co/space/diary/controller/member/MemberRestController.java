package kr.co.space.diary.controller.member;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberRestController {

  private final MemberService memberService;

  @GetMapping("me")
  public ResponseEntity<?> me(@AuthenticationPrincipal PrincipalDetails principalDetails) throws CustomException {
    if(principalDetails == null) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "UserDetails", "principalDetails");
    }
    return new ResponseEntity<>(memberService.findOne(Member.builder().id(principalDetails.getMember().getId()).build()), HttpStatus.OK);
  }

  @GetMapping("find-username")
  public ResponseEntity<?> findUsername(@RequestParam String name, @RequestParam String mobile) throws CustomException {
    return new ResponseEntity<>(memberService.findOne(Member.builder().name(name).mobile(mobile).build()), HttpStatus.OK);
  }

  @GetMapping("list")
  public ResponseEntity<?> list() throws CustomException {
    return new ResponseEntity<>(memberService.findAll(Member.builder().build()), HttpStatus.OK);
  }

  @PostMapping("/sign/up")
  public ResponseEntity<?> signUp(@RequestBody Member member) throws CustomException {
    memberService.create(member);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> modify(@PathVariable("id") String id, @RequestBody Member member) throws CustomException {
    member.setId(id);
    memberService.modify(member);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("change-password/{id}")
  public ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody Member member) throws CustomException {
    member.setId(id);
    memberService.changePassword(member);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> resign(@PathVariable("id") String id) throws CustomException {
    memberService.resign(Member.builder().id(id).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
