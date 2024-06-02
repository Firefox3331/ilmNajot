package uz.ilmnajot.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.enums.SchoolName;
import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.model.response.LoginResponse;
import uz.ilmnajot.school.model.response.UserResponse;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.repository.UserRepository;
import uz.ilmnajot.school.security.config.AuditingAwareConfig;
import uz.ilmnajot.school.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {



//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

//
//    @Override
//    public LoginResponse addUser(UserRequest request) {
//        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
//        if (userByEmail.isPresent()) {
//            throw new UserException("User is already exist", HttpStatus.CONFLICT);
//        }
//        if (!checkPassword(request)) {
//            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
//        }
//        User user = new User();
//        user.setFullName(request.getFullName());
//        user.setUsername(request.getUsername());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRoleName(RoleName.ADMIN);
//        user.setEnabled(true);
//        userRepository.save(user);
//        String token = jwtProvider.generateToken(user);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(token);
////        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
//        return loginResponse;
//    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }

    @Override
    public ApiResponse getUserById(Long userId) {
        Users user = getUser(userId);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new ApiResponse("User Found", true, userResponse);
    }

    @Override
    public ApiResponse getUsers() {
        List<Users> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> responseList = users
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("User response", responseList);
        responseMap.put("current page", 1);
        responseMap.put("totalItems", responseList.size());
        return new ApiResponse("Users Found", true, responseMap);
    }

    @Override
    public ApiResponse updateUser(Long userId, UserRequest request) {
        Users user = getUser(userId);
        user.setId(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosition(request.getPosition());
        user.setSchoolName(request.getSchoolName());
        user.setGender(request.getGender());
//        user.setPassword(request.getPassword());
//        user.setRoleName(request.getRoleName());
        Users savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("User Updated", true, userResponse);
    }

    @Override
    public ApiResponse deleteUser(Long userId) {
        getUser(userId);
        userRepository.deleteById(userId);
        return new ApiResponse("user deleted", true, "User deleted successfully");
    }

    @Override
    public ApiResponse getUserByName(String fullName) {
        Optional<Users> userByName = userRepository.findByFullName(fullName);
        if (userByName.isPresent()) {
            Users user = userByName.get();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        Optional<Users> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            Users user = userByEmail.get();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }


    @Override
    @Transactional
    public ApiResponse addUser(UserRequest request) {

        Optional<Role> defaultRole = roleRepository.findByName("USER");
        Role role = defaultRole.orElseThrow(() -> new UserException("role has not been found", HttpStatus.NOT_FOUND));

        Optional<Users> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPosition(request.getPosition());
        user.setSchoolName(request.getSchoolName());
        user.setRoles(Collections.singletonList(role));
        user.setGender(request.getGender());
        Users savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("success", true, userResponse);
    }

    @Override
    public ApiResponse assignRoleToUser(Long roleId, Long userId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<Users> optionalUser = userRepository.findById(userId);

        if (optionalRole.isPresent() && optionalUser.isPresent()) {
            Role role = optionalRole.get();
            Users user = optionalUser.get();
            user.getRoles().add(role);
            userRepository.save(user);
            return new ApiResponse("success", true, "role has been saved successfully");
        }
        throw new UserException("there is no existing role or user with id" + userId + " and  " + roleId);
    }

    @Override
    public ApiResponse removeRoleToUser(Long roleId, Long userId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Optional<Users> optionalUser = userRepository.findById(userId);

        if (optionalRole.isPresent() && optionalUser.isPresent()) {
            Role role = optionalRole.get();
            Users user = optionalUser.get();
            user.getRoles().remove(role);
            userRepository.save(user);
            return new ApiResponse("success", true, "role has been removed successfully");
        }
        throw new UserException("there is no existing role or user with id" + userId + " and  " + roleId);
    }

    @Override
    public HttpEntity<?> getAllUser() {
        return ResponseEntity.ok(userRepository.findAllByUsers());
    }

    private Users getUser(Long userId) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

}
