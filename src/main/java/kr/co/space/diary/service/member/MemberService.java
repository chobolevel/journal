package kr.co.space.diary.service.member;

import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.entity.member.Members;
import kr.co.space.diary.exception.CustomException;

import javax.mail.MessagingException;
import java.util.List;

public interface MemberService {

  int findCount();

  List<Member> findAll(Members members);

  Member findOne(Member member) throws CustomException;

  void create(Member member) throws CustomException;

  void modify(Member member) throws CustomException;

  void changePassword(Member member) throws CustomException;

  void initPassword(Member member) throws CustomException, MessagingException;

  void resign(Member member) throws CustomException;

}
