package kr.co.space.diary.entity.attachment;

import kr.co.space.diary.entity.common.Base;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment extends Base {

  private String diaryId;
  private String fileName;

}
