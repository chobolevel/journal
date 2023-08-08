package kr.co.space.diary.controller;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("profile")
  public String profile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
    if(principalDetails.getMember() == null) {
      return "redirect:/";
    }
    model.addAttribute("member", principalDetails.getMember());
    return "/member/profile";
  }

  @DeleteMapping("resign/{id}")
  public String resign(@PathVariable("id") String id) throws CustomException {
    memberService.resign(Member.builder().id(id).build());
    return "redirect:/";
  }

}
