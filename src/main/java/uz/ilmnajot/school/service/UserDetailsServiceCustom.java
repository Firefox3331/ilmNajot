package uz.ilmnajot.school.service;

import uz.ilmnajot.school.exception.UserException;
import uz.ilmnajot.school.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException("user not found"));
    }
}
