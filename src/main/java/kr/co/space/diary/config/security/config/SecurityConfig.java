package kr.co.space.diary.config.security.config;

import kr.co.space.diary.config.security.handler.CustomAuthenticationFailureHandler;
import kr.co.space.diary.config.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
  private final CustomAuthenticationFailureHandler authenticationFailureHandler;
  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/diary/**", "/api/diary/**").authenticated()
        .antMatchers("/member/sign/**",
            "/member/find-username",
            "/member/init-password",
            "/api/member/sign/up",
            "/api/member/init-password").anonymous()
        .antMatchers("/member/list","/api/member/list").hasRole("ADMIN")
        .antMatchers("/member/**", "/api/member/**").authenticated()
        .anyRequest().permitAll()
      .and()
        .formLogin()
        .loginPage("/member/sign/in")
        .loginProcessingUrl("/member/sign/in")
        .successHandler(authenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
      .and()
        .logout()
        .logoutUrl("/member/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
      .and()
        .sessionManagement()
        // 최대 세션 1개
        .maximumSessions(1)
        // true -> 인증 시도 세션 로그인 실패 처리
        // false -> 인증 시도 세션 로그인 성공 처리 및 기존 세션 만료
        .maxSessionsPreventsLogin(true);
    http
        .rememberMe()
        .rememberMeParameter("remember")
        .tokenValiditySeconds(60*60)
        .alwaysRemember(false)
        .userDetailsService(userDetailsService);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
