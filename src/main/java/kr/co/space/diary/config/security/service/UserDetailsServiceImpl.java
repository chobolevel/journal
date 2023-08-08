package kr.co.space.diary.config.security.service;

import kr.co.space.diary.config.security.principal.PrincipalDetails;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.mapper.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberMapper memberMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member findMember = memberMapper.findOne(Member.builder().username(username).build());
    if(findMember == null) {
      throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
    }
    return new PrincipalDetails(findMember);
  }
}
