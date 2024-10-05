package jla44.example.Trading.Platform.model;

import jla44.example.Trading.Platform.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
