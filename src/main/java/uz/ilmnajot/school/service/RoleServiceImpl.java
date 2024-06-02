package uz.ilmnajot.school.service;

import org.springframework.stereotype.Service;
import uz.ilmnajot.school.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
