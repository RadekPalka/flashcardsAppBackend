package pl.radekpalka.flashcardsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.radekpalka.flashcardsApp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
