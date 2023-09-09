package kr.co.space.diary.util;

import kr.co.space.diary.entity.common.Mail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

public class CommonUtil {

  public static String createTemporaryPassword() {
    // ints 통해 범위를 지정
    // limit 통해 길이를 지정
    // collect 통해 결과를 생성 아래코드는 append로 붙이는 코드
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    return random.ints(leftLimit, rightLimit + 1)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();

  }

  public static void sendTemporaryPassword(JavaMailSender mailSender, Mail mail) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
    mimeMessageHelper.setTo(mail.getTo());
    mimeMessageHelper.setSubject("[나만의 일기] 임시 비밀번호 발급");
    // 두번째 파라미터는 html 여부
    mimeMessageHelper.setText(String.format("임시 비밀번호는 %s 입니다.", mail.getTempPassword()), false);
    mailSender.send(mimeMessage);
  }

}
