package pl.polsl.polaczek.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.polaczek.models.entities.Photographer;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
