package net.javaguides.springboorblogwebapp.repository;

import net.javaguides.springboorblogwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
