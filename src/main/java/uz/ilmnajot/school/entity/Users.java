package uz.ilmnajot.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ilmnajot.school.base.BaseEntity;
import uz.ilmnajot.school.enums.Gender;
import uz.ilmnajot.school.enums.SchoolName;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Users extends BaseEntity implements UserDetails {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    @Size(min = 8, max = 25, message = "please try to check your email and must be between 8 and 25")
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String position; //enum

    @Enumerated(EnumType.STRING)
    private SchoolName schoolName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
//        return List.of(new SimpleGrantedAuthority(roles.toString()));
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
