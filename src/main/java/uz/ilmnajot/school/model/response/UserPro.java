package uz.ilmnajot.school.model.response;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserPro {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhoneNumber();
    String getGender();
    @Value("#{@roleRepository.findAllByUserId(target.id)}")
    List<RolePro> getRoles();
}
