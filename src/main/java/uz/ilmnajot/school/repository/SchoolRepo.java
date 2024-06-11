package uz.ilmnajot.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ilmnajot.school.entity.Schools;

public interface SchoolRepo extends JpaRepository<Schools, Long> {
}
