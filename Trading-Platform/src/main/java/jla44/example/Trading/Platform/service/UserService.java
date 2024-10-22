package jla44.example.Trading.Platform.service;

import jla44.example.Trading.Platform.domain.VerificationType;
import jla44.example.Trading.Platform.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(
            VerificationType verificationType,
            String sendTo,
            User user
    );

    User updatePassword(User user,String newPassword);

}
