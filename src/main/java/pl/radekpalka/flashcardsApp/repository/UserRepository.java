package pl.radekpalka.flashcardsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.radekpalka.flashcardsApp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository {
    boolean existsByLogin(String login);

    Optional<User> findByUser(String login);
}
