package kr.co.space.diary.config.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final RequestCache requestCache = new HttpSessionRequestCache();
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    // 요청 캐시에 기존에 요청한 URL 정보가 없는 경우 홈화면으로 이동하는 successHandler
    setDefaultTargetUrl("/");
    SavedRequest savedRequest = requestCache.getRequest(request, response);
    if(savedRequest != null) {
      String redirectUrl = savedRequest.getRedirectUrl();
      redirectStrategy.sendRedirect(request, response, redirectUrl);
    } else {
      redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
    }
  }
}
