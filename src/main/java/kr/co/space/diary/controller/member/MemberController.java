package kr.co.space.diary.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("member")
public class MemberController {

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

  @GetMapping("init-password")
  public String initPassword() {
    return "/member/init-password";
  }

  @GetMapping("list")
  public String manageMember() {
    return "/member/list";
  }

}
