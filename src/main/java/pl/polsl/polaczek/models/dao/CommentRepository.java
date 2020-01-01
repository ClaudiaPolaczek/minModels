package pl.polsl.polaczek.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.polaczek.models.entities.Comment;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRatingUser_Username(String ratingUserUsername);
    List<Comment> findAllByRatedUser_Username(String ratedUserUsername);
    List<Comment> findAllByRatingUserUsernameAndRatedUserUsername(String ratingUserUsername, String ratedUserUsername);
}
