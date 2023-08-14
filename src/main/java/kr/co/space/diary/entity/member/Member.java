package kr.co.space.diary.entity.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.space.diary.entity.common.Base;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private String nickname;
    private MemberRoleType role;
    private String activateYn;

}
