package kr.co.space.diary.config.security.config;

import kr.co.space.diary.enums.member.MemberRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/diary/**").hasAuthority(MemberRoleType.ROLE_USER.getName())
        .anyRequest().permitAll()
      .and()
        .formLogin()
        .loginPage("/member/sign/in")
        .loginProcessingUrl("/member/sign/in")
        .defaultSuccessUrl("/")
      .and()
        .logout()
        .logoutUrl("/member/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true).deleteCookies("JSESSIONID");
  }
}
