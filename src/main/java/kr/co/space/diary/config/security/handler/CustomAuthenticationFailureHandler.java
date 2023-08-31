package kr.co.space.diary.config.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    String errorMessage = "회원정보가 올바르지 않습니다.";

    // 추가적인 예외 사항에 대해 작성하기
    if(exception instanceof BadCredentialsException) {
      errorMessage = "회원정보가 올바르지 않습니다.";
    }

    setDefaultFailureUrl("/member/sign/in?error=true&exception=" + URLEncoder.encode(errorMessage));

    // successHandler와 달리 부모 요청하는 이유는 인증 실패와 관련된 추가 동작을 진행하기 위함
    super.onAuthenticationFailure(request, response, exception);
  }

}
