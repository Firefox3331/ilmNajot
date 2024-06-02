package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.Query;
import uz.ilmnajot.school.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.school.model.response.UserPro;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

//    @Query(value = "select * from users  where users.email like %?1%", nativeQuery = true)
    Optional<Users> findByEmail(/*@Param("email") */String username);

    @Query(value = "SELECT * FROM user WHERE users.fullName LIKE %?1%", nativeQuery = true)
    Optional<Users> findByFullName(String fullName);
    @Query(value="select id, first_name as firstName, last_name as lastName, email, gender, phone_number as phoneNumber from users", nativeQuery = true)
    List<UserPro> findAllByUsers();
}
