package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Model;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
