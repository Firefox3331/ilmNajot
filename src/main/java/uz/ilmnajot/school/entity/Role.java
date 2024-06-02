package uz.ilmnajot.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.RoleName;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Users> users;


    public static Role of(String name){
        Role role = new Role();
        role.setName(name);
        return role;
    }
 }
