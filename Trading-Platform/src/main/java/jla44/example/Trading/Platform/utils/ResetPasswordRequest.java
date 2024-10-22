package jla44.example.Trading.Platform.utils;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
