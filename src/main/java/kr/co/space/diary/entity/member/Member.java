package kr.co.space.diary.entity.member;

import kr.co.space.diary.entity.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Base {

    private String id;
    private String username;
    private String password;
    private String name;
    private String nickname;
    private String role;

}
