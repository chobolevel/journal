package kr.co.space.diary.controller.member;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberRestController {

  private final MemberService memberService;

  @GetMapping("me")
  public Member me(@AuthenticationPrincipal PrincipalDetails principalDetails) throws CustomException {
    return memberService.findOne(Member.builder().id(principalDetails.getMember().getId()).build());
  }

  @GetMapping("list")
  public List<Member> list() throws CustomException {
    return memberService.findAll(Member.builder().build());
  }

  @PostMapping("/sign/up")
  public HttpStatus signUp(@RequestBody Member member) throws CustomException {
    memberService.create(member);
    return HttpStatus.CREATED;
  }

  @PutMapping("{id}")
  public HttpStatus modify(@PathVariable("id") String id, @RequestBody Member member) throws CustomException {
    member.setId(id);
    memberService.modify(member);
    return HttpStatus.OK;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> resign(@PathVariable("id") String id) throws CustomException {
    memberService.resign(Member.builder().id(id).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
