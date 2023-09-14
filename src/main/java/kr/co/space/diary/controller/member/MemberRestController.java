package kr.co.space.diary.controller.member;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.common.HttpResult;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.entity.member.Members;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

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
  public ResponseEntity<?> findUsername(@RequestParam String name, @RequestParam String email) throws CustomException {
    return new ResponseEntity<>(memberService.findOne(Member.builder().name(name).email(email).build()), HttpStatus.OK);
  }

  @GetMapping("check-username")
  public ResponseEntity<?> checkUsername(@RequestParam String username) throws CustomException {
    Member findMember = memberService.findOne(Member.builder().username(username).build());
    if(findMember != null) {
      return new ResponseEntity<>(Member.builder().isUsernameExists(true).build(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(Member.builder().isUsernameExists(false).build(), HttpStatus.OK);
    }
  }

  @GetMapping("list")
  public ResponseEntity<?> list(Members members) throws CustomException {
    List<Member> memberList = memberService.findAll(members);
    members.setMemberList(memberList);
    members.setTotalCnt(memberService.findCount());
    return new ResponseEntity<>(members, HttpStatus.OK);
  }

  @PostMapping("/sign/up")
  public ResponseEntity<?> signUp(@RequestBody Member member) throws CustomException {
    memberService.create(member);
    return new ResponseEntity<>(HttpResult.create("success to create member"), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> modify(@PathVariable("id") String id, @RequestBody Member member) throws CustomException {
    member.setId(id);
    memberService.modify(member);
    return ResponseEntity.ok(HttpResult.ok("success to modify member"));
  }

  @PutMapping("change-password/{id}")
  public ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody Member member) throws CustomException {
    member.setId(id);
    memberService.changePassword(member);
    return ResponseEntity.ok(HttpResult.ok("success to change password"));
  }

  @PutMapping("init-password")
  public ResponseEntity<?> initPassword(@RequestBody Member member) throws CustomException, MessagingException {
    memberService.initPassword(member);
    return ResponseEntity.ok(HttpResult.ok("success to init password"));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> resign(@PathVariable("id") String id) throws CustomException {
    memberService.resign(Member.builder().id(id).build());
    return ResponseEntity.ok(HttpResult.ok("success to resign member"));
  }

}
