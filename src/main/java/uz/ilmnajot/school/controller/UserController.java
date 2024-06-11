package uz.ilmnajot.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/add-role/{userId}/{roleId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpEntity<?> assignRoleToUser(@PathVariable Long roleId, @RequestHeader String Authorization, @PathVariable Long userId){
        System.out.println("role_id is: " + roleId + ", user_id is: " + userId);
        return userService.assignRoleToUser(roleId, userId, Authorization);
    }


}
