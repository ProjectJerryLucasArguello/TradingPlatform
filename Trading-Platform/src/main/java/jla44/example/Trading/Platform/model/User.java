package jla44.example.Trading.Platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jla44.example.Trading.Platform.domain.USER_ROLE;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    //USer has to write the password or else they are ignored to be registered
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth= new TwoFactorAuth();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role =USER_ROLE.ROLE_CUSTOMER;
}
