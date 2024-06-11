package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ilmnajot.school.entity.Roles;
import uz.ilmnajot.school.entity.Users;
import uz.ilmnajot.school.projection.RolePro;

import java.util.List;

public interface RoleRepo extends JpaRepository<Roles, Long> {
    @Query(value = "select rb.id, rb.name,\n" +
            "       coalesce(rb.id =\n" +
            "                (select r.id from roles r\n" +
            "                    join users_roles ur on r.id = ur.roles_id\n" +
            "                             where ur.users_id = :id and r.id=rb.id),\n" +
            "                false) as hasrole\n" +
            "            from roles rb where rb.id != 2;", nativeQuery = true)
    List<RolePro> findAllByUserId(Long id);
}
