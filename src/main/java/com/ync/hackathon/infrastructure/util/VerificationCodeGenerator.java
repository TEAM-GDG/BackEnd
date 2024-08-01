package com.ync.hackathon.infrastructure.util;

import java.util.Random;

public class VerificationCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generate() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10)); // 0-9 랜덤 숫자
        }
        return code.toString();
    }
}
