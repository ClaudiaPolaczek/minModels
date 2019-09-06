package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Photographer;

import java.util.Optional;

public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
