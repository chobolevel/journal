package kr.co.space.diary.entity.member;

import kr.co.space.diary.entity.Base;
import kr.co.space.diary.enums.member.MemberRoleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends Base {

    private String id;
    private String username;
    private String password;
    private String name;
    private String nickname;
    private MemberRoleType role;

}
