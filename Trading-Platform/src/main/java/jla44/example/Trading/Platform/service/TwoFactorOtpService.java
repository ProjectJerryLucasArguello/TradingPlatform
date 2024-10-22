package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.model.TwoFactorAuth;
import jla44.example.Trading.Platform.model.TwoFactorOTP;
import jla44.example.Trading.Platform.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
