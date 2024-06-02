package uz.ilmnajot.school.service;

import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.LoginForm;
import uz.ilmnajot.school.model.request.UserRequest;


public interface AuthService {

    ApiResponse register(UserRequest form);

//    LoginResponse addUser(UserRequest request);

    ApiResponse authenticate(LoginForm form);
}
