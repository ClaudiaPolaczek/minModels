package pl.polsl.polaczek.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.polaczek.models.entities.PhotoShoot;

import java.util.List;

@Repository
public interface PhotoShootRepository extends JpaRepository<PhotoShoot, Long> {
    List<PhotoShoot> findAllByInvitingUser_Username(String invitingUserUsername);
    List<PhotoShoot> findAllByInvitedUser_Username(String invitedUserUsername);
}
