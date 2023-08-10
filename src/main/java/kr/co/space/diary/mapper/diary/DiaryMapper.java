package kr.co.space.diary.mapper.diary;

import kr.co.space.diary.entity.common.Criteria;
import kr.co.space.diary.entity.diary.Diary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

  int findCount();

  List<Diary> findAll(Diary diary, Criteria cri);

  Diary findOne(Diary diary);

  void create(Diary diary);

  void modify(Diary diary);

  void remove(Diary diary);

}
