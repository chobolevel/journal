package kr.co.space.diary.entity.diary;

import kr.co.space.diary.entity.common.Base;
import kr.co.space.diary.entity.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary extends Base {

    private String id;
    private String title;
    private String content;
    private String writerId;
    private String publicYn;

    private Member writer;

}
