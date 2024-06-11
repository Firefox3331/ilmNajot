package uz.ilmnajot.school.projection;

import org.springframework.beans.factory.annotation.Value;
import uz.ilmnajot.school.entity.Schools;
import uz.ilmnajot.school.enums.Gender;

import java.util.List;

public interface UserPro {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getUsername();
    String getPhoneNumber();
    String getGender();
    String getSchool();
    @Value("#{@roleRepo.findAllByUserId(target.id)}")
    List<RolePro> getRoles();
}
