package jla44.example.Trading.Platform.utils;


import jla44.example.Trading.Platform.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
