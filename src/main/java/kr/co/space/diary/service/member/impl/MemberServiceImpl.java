package kr.co.space.diary.service.member.impl;

import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.mapper.member.MemberMapper;
import kr.co.space.diary.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;

  @Override
  public List<Member> findAll(Member member) {
    return memberMapper.findAll(member);
  }

  @Override
  public Member findOne(Member member) throws CustomException {
    if(member.getId() == null || member.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    return memberMapper.findOne(member);
  }

  @Override
  public void create(Member member) throws CustomException {
    if(member.getUsername() == null || member.getUsername().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "username");
    }
    else if(member.getPassword() == null || member.getPassword().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "password");
    }
    member.setId(UUID.randomUUID().toString());
    memberMapper.create(member);
  }

  @Override
  public void modify(Member member) throws CustomException {
    if(member.getId() == null || member.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    memberMapper.modify(member);
  }

  @Override
  public void remove(Member member) throws CustomException {
    if(member.getId() == null || member.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    memberMapper.remove(member);
  }
}
