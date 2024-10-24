package jla44.example.Trading.Platform.controller;

import jla44.example.Trading.Platform.domain.VerificationType;
import jla44.example.Trading.Platform.model.ForgotPasswordToken;
import jla44.example.Trading.Platform.model.User;
import jla44.example.Trading.Platform.model.VerificationCode;
import jla44.example.Trading.Platform.response.AuthResponse;
import jla44.example.Trading.Platform.service.EmailService;
import jla44.example.Trading.Platform.service.ForgotPasswordService;
import jla44.example.Trading.Platform.service.UserService;
import jla44.example.Trading.Platform.service.VerificationCodeService;
import jla44.example.Trading.Platform.utils.ApiResponse;
import jla44.example.Trading.Platform.utils.ForgotPasswordTokenRequest;
import jla44.example.Trading.Platform.utils.OtpUtils;
import jla44.example.Trading.Platform.utils.ResetPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;
    private String jwt;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode =verificationCodeService
                .getVerificationCodeByUser(user.getId());

        //Make note of the conditional statment of this line below
        if(verificationCode==null){
            verificationCode=verificationCodeService
                    .sendVerificationCode(user, verificationType);
        }

        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(
                    user.getEmail(),
                    verificationCode.getOtp());
        }

        return new ResponseEntity<String>("Verification otp sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user =userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified=verificationCode.getOtp().equals(otp);

        if(isVerified){
            User updatedUser= userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(),
                    sendTo,user
                    );

            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Wrong otp");
    }
    //Write two methods one to send to token and verify token, second update password
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest request
            ) throws Exception {

        User user = userService.findUserByEmail(request.getSendTo());
        String otp= OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id=uuid.toString();

        ForgotPasswordToken token= forgotPasswordService.findByUser(user.getId());

        if(token == null){
            token =forgotPasswordService.createToken(user,id,otp,request.getVerificationType(),request.getSendTo());

        }

        if(request.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(
                    user.getEmail(),
                    token.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset otp sent successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Verification of otp
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {



        ForgotPasswordToken forgotPasswordToken= forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getVerificationType().equals(request.getOtp());

        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), request.getPassword());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("password update successfully");
            return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
        } throw new Exception("Wrong otp");

    }
}
