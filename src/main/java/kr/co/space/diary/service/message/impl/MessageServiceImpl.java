package kr.co.space.diary.service.message.impl;

import kr.co.space.diary.exception.CustomException;
import kr.co.space.diary.service.message.MessageService;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

  private final DefaultMessageService messageService;

  public MessageServiceImpl() {
    this.messageService = NurigoApp.INSTANCE.initialize("NCSRXQUNFJ9JXP6T", "S5RB46O3ODI8WMEBIWC2OJYSE6P9LV9B", "https://api.coolsms.co.kr");
  }

  @Override
  public void sendSms() throws CustomException {
    Message message = new Message();
    message.setFrom("01095657072");
    message.setTo("01095657072");
    message.setText("[오늘의 일기] 인증번호는 000218 입니다.");
    SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    System.out.println(response);
  }

}
