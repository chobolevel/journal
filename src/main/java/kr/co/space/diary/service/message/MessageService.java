package kr.co.space.diary.service.message;

import kr.co.space.diary.exception.CustomException;

public interface MessageService {

  void sendSms() throws CustomException;

}
