package uz.ilmnajot.school.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.payload.Login;
import uz.ilmnajot.school.payload.Register;

@Service
public interface AuthService {
    HttpEntity<?> login(Login login);

    HttpEntity<?> register(Register register);

    Users saveUser(Register register);
}
