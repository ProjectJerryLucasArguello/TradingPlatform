package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.domain.VerificationType;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
