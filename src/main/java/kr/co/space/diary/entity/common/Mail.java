package kr.co.space.diary.entity.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Mail {

  private String to;

  private String tempPassword;

}
