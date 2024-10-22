package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.domain.VerificationType;
import jla44.example.Trading.Platform.model.ForgotPasswordToken;
import jla44.example.Trading.Platform.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

}
