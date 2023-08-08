package kr.co.space.diary.controller;

import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/sign/in")
  public String signIn() {
    return "/member/sign/in";
  }

  @GetMapping("/sign/up")
  public String signUp() {
    return "/member/sign/up";
  }

  @PostMapping("/sign/up")
  public String signUp(Member member) throws CustomException {
    memberService.create(member);
    return "redirect:/member/sign/in";
  }

}
