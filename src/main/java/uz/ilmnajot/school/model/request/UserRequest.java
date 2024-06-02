package uz.ilmnajot.school.model.request;

import lombok.Data;
import uz.ilmnajot.school.entity.Role;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.RoleName;
import uz.ilmnajot.school.enums.SchoolName;

import java.util.List;

@Data
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String position;

    private SchoolName schoolName;

    private List<Role> roles;

    private Gender gender;

    private String password;

    private String rePassword;


//    private RoleName roleName;

}


