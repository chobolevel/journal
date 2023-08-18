package kr.co.space.diary.config.security.config;

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
        .antMatchers("/diary/**").authenticated()
        .antMatchers("/api/member/list").hasAuthority("ROLE_ADMIN")
        .antMatchers("/member/sign/up", "/member/sign/in").anonymous()
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
