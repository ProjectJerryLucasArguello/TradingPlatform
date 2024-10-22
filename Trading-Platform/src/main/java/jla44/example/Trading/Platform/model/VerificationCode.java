package jla44.example.Trading.Platform.model;

import jakarta.persistence.*;
import jla44.example.Trading.Platform.domain.VerificationType;
import lombok.Data;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private User user;

    private String email;

    private String mobile;

    private VerificationType verificationType;
}
