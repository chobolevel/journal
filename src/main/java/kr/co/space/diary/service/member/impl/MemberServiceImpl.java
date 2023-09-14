package kr.co.space.diary.service.member.impl;

import kr.co.space.diary.entity.common.Mail;
import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.entity.member.Members;
import kr.co.space.diary.enums.common.CustomExceptionType;
import kr.co.space.diary.enums.member.MemberResignType;
import kr.co.space.diary.enums.member.MemberRoleType;
import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.mapper.member.MemberMapper;
import kr.co.space.diary.service.member.MemberService;
import kr.co.space.diary.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberMapper memberMapper;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JavaMailSender mailSender;

  @Override
  public int findCount() {
    return memberMapper.findCount();
  }

  @Override
  public List<Member> findAll(Members members) {
    return memberMapper.findAll(members);
  }

  @Override
  public Member findOne(Member member) throws CustomException {
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
    else if(member.getName() == null || member.getName().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "name");
    }
    else if(member.getNickname() == null || member.getNickname().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "nickname");
    }
    else if(member.getEmail() == null || member.getEmail().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "email");
    }

    Member sameUsernameMember = memberMapper.findOne(Member.builder().username(member.getUsername()).build());

    if(sameUsernameMember != null) {
      throw new CustomException(CustomExceptionType.SAME_USERNAME_EXISTS);
    }

    member.setId(UUID.randomUUID().toString());
    member.setPassword(passwordEncoder.encode(member.getPassword()));
    member.setRole(MemberRoleType.ROLE_USER);
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
  public void changePassword(Member member) throws CustomException {
    if(member.getPassword() == null || member.getPassword().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "password");
    } else if(member.getToChangePassword() == null || member.getToChangePassword().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "toChangePassword");
    }
    Member findMember = memberMapper.findOne(member);
    if(passwordEncoder.matches(member.getPassword(), findMember.getPassword())) {
      member.setToChangePassword(passwordEncoder.encode(member.getToChangePassword()));
      memberMapper.modify(member);
    } else {
      throw new CustomException(CustomExceptionType.NOT_MATCH_PASSWORD);
    }
  }

  @Override
  public void initPassword(Member member) throws CustomException, MessagingException {
    // member 객체는 username, email 값만 가짐
    Member findMember = memberMapper.findOne(Member.builder().username(member.getUsername()).email(member.getEmail()).build());
    if(findMember == null) {
      throw new CustomException(CustomExceptionType.NOT_MATCH_USERNAME_OR_EMAIL);
    }
    String temporaryPassword = CommonUtil.createTemporaryPassword();
    CommonUtil.sendTemporaryPassword(mailSender,
        Mail.builder().to(findMember.getEmail()).tempPassword(temporaryPassword).build());
    memberMapper.modify(Member.builder().id(findMember.getId()).toChangePassword(passwordEncoder.encode(temporaryPassword)).build());
  }

  @Override
  public void resign(Member member) throws CustomException {
    if(member.getId() == null || member.getId().isEmpty()) {
      throw new CustomException(CustomExceptionType.MISSING_PARAMETER, "String", "id");
    }
    member.setResignYn(MemberResignType.RESIGN.getName());
    memberMapper.modify(member);
  }
}
