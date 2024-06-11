package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.projection.UserPro;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<Users> findByEmail(String username);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<Users> findByEmails(String email);

    @Query(value = "select u.id as id, u.firstName as  firstName, u.lastName as lastName, u.email as email, u.username as username, u.gender as gender, u.school.name as school, u.phoneNumber as phoneNumber from Users u", nativeQuery = false)
    List<UserPro> findAllByUsers();

    UserDetails findByUsername(String username);
}
