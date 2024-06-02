package uz.ilmnajot.school.controller;

//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.school.model.common.ApiResponse;
import uz.ilmnajot.school.model.request.UserRequest;
import uz.ilmnajot.school.repository.RoleRepository;
import uz.ilmnajot.school.service.UserService;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public HttpEntity<ApiResponse> addUser(@RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.addUser(request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/getUser/{userId}")
    public HttpEntity<ApiResponse> getUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.getUserById(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/getUsers")
    public HttpEntity<ApiResponse> getUsers() {
        ApiResponse apiResponse = userService.getUsers();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/updateUser/{userId}")
    public HttpEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.updateUser(userId, request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.deleteUser(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public HttpEntity<?> getUserByName() {
        return userService.getAllUser();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/getUserByEmail")
    public HttpEntity<ApiResponse> getUserByEmail(@RequestParam(name = "email") String email) {
        ApiResponse apiResponse = userService.getUserByEmail(email);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Autowired
    RoleRepository roleRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assignRoleToUser/{roleId}/{userId}")
    public HttpEntity<?> assignRoleToUser(
            @PathVariable(name = "roleId") Long roleId,
            @PathVariable(name = "userId") Long userId){
        System.out.println("assignRoleToUser");
        ApiResponse apiResponse = userService.assignRoleToUser(roleId, userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/removeRoleToUser/{roleId}/{userId}")
    public HttpEntity<ApiResponse> removeRoleToUser(
            @PathVariable(name = "roleId") Long roleId,
            @PathVariable(name = "userId") Long userId){
        ApiResponse apiResponse = userService.removeRoleToUser(roleId, userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


}

