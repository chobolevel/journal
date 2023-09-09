package kr.co.space.diary.entity.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class HttpResult {

  private HttpStatus statusCode;
  private String statusText;
  private String message;

  public static HttpResult ok(String message) {
    return HttpResult.builder().statusCode(HttpStatus.OK).statusText(HttpStatus.OK.getReasonPhrase()).message(message).build();
  }

}
