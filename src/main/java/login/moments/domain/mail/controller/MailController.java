package login.moments.domain.mail.controller;

import login.moments.domain.mail.dto.MailSendDto;
import login.moments.domain.mail.dto.MessageResponseDto;
import login.moments.domain.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/sendVerificationCodeMail")
    public MessageResponseDto sendVerificationCodeMail(@RequestBody MailSendDto mailSendDto) {
        return mailService.sendVerificationCode(mailSendDto);
    }
}
