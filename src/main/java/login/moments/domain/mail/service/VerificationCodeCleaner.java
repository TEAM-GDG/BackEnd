package login.moments.domain.mail.service;

import login.moments.domain.mail.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VerificationCodeCleaner {

    private final VerificationCodeRepository verificationCodeRepository;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void cleanExpiredCodes() {
        LocalDateTime now = LocalDateTime.now();
        verificationCodeRepository.findAll().forEach(code -> {
            if (code.getExpiredAt().isBefore(now)) {
                verificationCodeRepository.deleteById(code.getId());
            }
        });
    }
}
