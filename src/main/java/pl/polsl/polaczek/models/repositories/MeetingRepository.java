package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
