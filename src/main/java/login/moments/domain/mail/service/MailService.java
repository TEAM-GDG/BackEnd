package login.moments.domain.mail.service;

import login.moments.domain.mail.VerificationCode;
import login.moments.domain.mail.dto.MailSendDto;
import login.moments.domain.mail.dto.MessageResponseDto;
import login.moments.domain.mail.repository.VerificationCodeRepository;
import login.moments.domain.user.repository.UserRepository;
import login.moments.global.jwt.util.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    public MessageResponseDto sendVerificationCode(MailSendDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return new MessageResponseDto("이미 사용 중인 이메일입니다. 다른 이메일을 사용해 주세요.");
        }

        String code = VerificationCodeGenerator.generate();

        String subject = "[moments] 회원가입을 위한 인증 코드입니다.";

        String content = String.format(
                "<div style=\"font-family: Arial, sans-serif; padding: 20px; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;\">" +
                        "    <div style=\"text-align: center;\">" +
                        "        <img src=\"https://i.postimg.cc/xCKWST1h/moments-logo.png\" alt=\"Moments 로고\" style=\"width: 150px; height: auto;\" />" +
                        "        <h2 style=\"color: #333;\">회원가입 인증 코드</h2>" +
                        "        <p style=\"color: #666; font-size: 14px;\">moments에 오신 것을 환영합니다! 아래의 인증 코드를 사용하여 회원가입을 완료하세요.</p>" +
                        "    </div>" +
                        "    <div style=\"text-align: center; margin: 20px 0;\">" +
                        "        <h1 style=\"font-size: 32px; color: #ADC2A6; margin: 0;\">%s</h1>" +
                        "    </div>" +
                        "    <div style=\"text-align: center;\">" +
                        "        <p style=\"color: #666; font-size: 12px;\">이 코드는 10분 동안 유효합니다. 문제가 발생하거나 도움이 필요하시면 지원팀에 문의하세요.</p>" +
                        "    </div>" +
                        "</div>",
                code
        );

        sendEmail(dto.getEmail(), subject, content);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setEmail(dto.getEmail());
        verificationCode.setCreatedAt(LocalDateTime.now());
        verificationCode.setExpiredAt(LocalDateTime.now().plusMinutes(10));

        verificationCodeRepository.save(verificationCode);

//        redisTemplate.opsForValue().set(code, dto.getEmail(), 10, TimeUnit.MINUTES);

        return new MessageResponseDto("인증 코드를 다음 메일로 전송하였습니다. : " + dto.getEmail());
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom("likelionync.moments@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }
}
