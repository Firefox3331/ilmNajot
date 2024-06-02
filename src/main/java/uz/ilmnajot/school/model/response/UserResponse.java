package uz.ilmnajot.school.model.response;

import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ilmnajot.school.enums.SchoolName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private  String email;

    private String phoneNumber;

    private String position;

    private SchoolName schoolName;

    private RoleName roleName;

    private Gender gender;

    //

//    private String password;
//
//    private String rePassword;
}
