package jla44.example.Trading.Platform.model;


import jakarta.persistence.*;
import jla44.example.Trading.Platform.domain.VerificationType;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private User user;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;
}
