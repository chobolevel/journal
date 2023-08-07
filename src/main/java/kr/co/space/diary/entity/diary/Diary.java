package kr.co.space.diary.entity.diary;

import kr.co.space.diary.entity.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diary extends Base {

    private String id;
    private String title;
    private String content;
    private String writer;

}
