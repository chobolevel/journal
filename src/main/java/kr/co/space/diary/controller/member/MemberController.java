package kr.co.space.diary.controller.member;

import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/sign/in")
  public String signIn(@RequestParam(required = false) boolean error,
                       @RequestParam(value = "exception", required = false) String errorMessage,
                       Model model) {
    model.addAttribute("error", error);
    model.addAttribute("errorMessage", errorMessage);
    return "/member/sign/in";
  }

  @GetMapping("/sign/up")
  public String signUp() {
    return "/member/sign/up";
  }

  @GetMapping("profile")
  public String profile() {
    return "/member/profile";
  }

  @GetMapping("modify")
  public String modify() {
    return "/member/modify";
  }

  @GetMapping("find-username")
  public String findUsername() {
    return "/member/find-username";
  }

  @GetMapping("change-password")
  public String changePassword() {
    return "/member/change-password";
  }

}
