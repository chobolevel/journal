package kr.co.space.diary.mapper.member;

import kr.co.space.diary.entity.member.Member;
import kr.co.space.diary.entity.member.Members;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

  int findCount();
  List<Member> findAll(Members members);

  Member findOne(Member member);

  void create(Member member);

  void modify(Member member);

}
