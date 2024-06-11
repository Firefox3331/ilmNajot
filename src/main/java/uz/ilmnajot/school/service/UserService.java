package uz.ilmnajot.school.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    HttpEntity<?> getAllUsers();

    HttpEntity<?> assignRoleToUser(Long roleId, Long userId, String authorization);

}
