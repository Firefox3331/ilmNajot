package uz.ilmnajot.school.service;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.Roles;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.payload.Login;
import uz.ilmnajot.school.payload.Register;
import uz.ilmnajot.school.repository.RoleRepo;
import uz.ilmnajot.school.repository.SchoolRepo;
import uz.ilmnajot.school.repository.UserRepo;
import uz.ilmnajot.school.service.jwt.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SchoolRepo schoolRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public HttpEntity<?> login(Login login) {
        Optional<Users> userOpt = userRepo.findByEmail(login.username());
        HashMap<String, String> response = new HashMap<>();
        if(userOpt.isPresent()){
            if(userOpt.get().isEnabled()){
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                login.username(), login.password()
                        )
                );
                Users save = userRepo.save(userOpt.get());
                response.put("token", jwtService.generateAccessToken(save));
            }
        }else{
            response.put("message", "User not found!");
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> register(Register register) {
        HashMap<String, String> response = new HashMap<>();
        Optional<Users> userByEmail = userRepo.findByEmail(register.email());
        if (!register.email().isBlank()){
            if(!userByEmail.isPresent()){
                if(register.password().equals(register.rePassword())){
                    Users user = saveUser(register);
                    user.setRoles(List.of(roleRepo.findById(1L).get()));
                    Users savedUser = userRepo.save(user);
                    response.put("token", jwtService.generateAccessToken(savedUser));
                }else{
                    response.put("message", "Password did not match!");
                }
            }else {
                response.put("message", "This email already exists!");
            }
        }else{
            response.put("message", "Email did not match!");
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public Users saveUser(Register register) {
        Users users = Users.builder()
                .firstName(register.firstName())
                .lastName(register.lastName())
                .email(register.email())
                .username(register.username())
                .phoneNumber(register.phoneNumber())
                .gender(Gender.valueOf(register.gender()))
                .school(schoolRepo.findById(register.schoolId()).get())
                .password(passwordEncoder.encode(register.password())).build();
        if(register.id() != null) users.setId(register.id());
        return users;
    }


}
