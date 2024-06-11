package uz.ilmnajot.school.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ilmnajot.school.entity.Roles;
import uz.ilmnajot.school.entity.Schools;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.repository.RoleRepo;
import uz.ilmnajot.school.repository.SchoolRepo;
import uz.ilmnajot.school.repository.UserRepo;

import java.util.List;
@Configuration
public class CommandLineConfig implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final SchoolRepo schoolRepo;
    private final UserRepo userRepo;

    public CommandLineConfig(PasswordEncoder passwordEncoder, RoleRepo roleRepo, SchoolRepo schoolRepo, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.schoolRepo = schoolRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Roles> roles = roleRepo.saveAll(List.of(
                new Roles(1L, "ROLE_USER"),
                new Roles(2L, "ROLE_SUPER_ADMIN"),
                new Roles(3L, "ROLE_ADMIN"),
                new Roles(4L, "ROLE_TEACHER"),
                new Roles(5L, "ROLE_STUDENT")
        ));
        Schools school = schoolRepo.save(new Schools(1L, "SAMARQAND PRESEDENTIAL SCHOOL"));

        Users user = Users.builder()
                .id(1L)
                .firstName("superadmin")
                .lastName("superadmin")
                .email("superadmin")
                .username("superadmin")
                .phoneNumber("+998953500075")
                .gender(Gender.valueOf("MALE"))
                .school(school)
                .roles(roles)
                .password(passwordEncoder.encode("root123")).build();
        userRepo.save(user);
    }
}
