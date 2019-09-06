package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
