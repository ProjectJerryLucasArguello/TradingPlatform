package jla44.example.Trading.Platform.repository;

import jla44.example.Trading.Platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

}
