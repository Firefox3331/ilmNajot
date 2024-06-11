package uz.ilmnajot.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.school.payload.Login;
import uz.ilmnajot.school.payload.Register;
import uz.ilmnajot.school.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/sign-in")
    public HttpEntity<?> login(@RequestBody Login login){
        return authService.login(login);
    }

    @PostMapping("/sign-up")
    public HttpEntity<?> register(@RequestBody Register register){
        return authService.register(register);
    }
}
