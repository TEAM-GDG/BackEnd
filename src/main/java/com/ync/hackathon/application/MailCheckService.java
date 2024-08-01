package com.ync.hackathon.application;

import com.ync.hackathon.presentation.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCheckService {
    private final VerificationService verificationService;

    public MessageResponseDto getEmailByToken(String token) {
        if(verificationService.getEmailByToken(token) == null){
            return new MessageResponseDto("유효하지 않거나 만료된 토큰입니다.");
        } else {
            return new MessageResponseDto("토큰이 유효합니다. 비밀번호를 재설정할 수 있습니다.");
        }
    }

}
