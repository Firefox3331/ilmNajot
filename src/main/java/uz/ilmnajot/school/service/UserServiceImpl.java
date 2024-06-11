package uz.ilmnajot.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.ilmnajot.school.entity.Roles;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.projection.AddRole;
import uz.ilmnajot.school.projection.HasRole;
import uz.ilmnajot.school.repository.RoleRepo;
import uz.ilmnajot.school.repository.UserRepo;
import uz.ilmnajot.school.service.jwt.JwtService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAllByUsers());
    }

    @Override
    public HttpEntity<?> assignRoleToUser(Long roleId, Long userId, String authorization) {
        HashMap<String, String> response = new HashMap<>();
        String email = jwtService.extractSubject(authorization);
        Optional<Users> admin = userRepo.findByEmails(email);
        Optional<Users> userOpt = userRepo.findById(userId);
        Optional<Roles> roleOpt = roleRepo.findById(roleId);
        if(userOpt.isPresent()){
            if(roleOpt.isPresent()){
                List<HasRole> query = jdbcTemplate.query("select\n" +
                        "    case when\n" +
                        "    roles_id=" + roleId + " and users_id=" + userId + " then\n" +
                        "    true else false end as hasrole\n" +
                        "\n" +
                        "from users_roles\n" +
                        "where users_id=" + userId + " and roles_id=" + roleId, BeanPropertyRowMapper.newInstance(HasRole.class));
                if(query.size()>0){
                    response.put("message", "deleting have done successful");
                }else{
                    response.put("message", "role adding have done successful");
                }
                List<AddRole> query1 = jdbcTemplate.query("select add_or_delete_role(" + roleId + "::bigint, " + userId + "::bigint);", BeanPropertyRowMapper.newInstance(AddRole.class));
                System.out.println(query1);

            }else{
                response.put("message", "role did not found");
            }
        }else{
            response.put("message", "user did not found");
        }
        return ResponseEntity.ok(response);
    }


}
