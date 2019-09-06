package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
