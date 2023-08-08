package kr.co.space.diary.mapper.member;

import kr.co.space.diary.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

  List<Member> findAll(Member member);

  Member findOne(Member member);

  void create(Member member);

  void modify(Member member);

}
