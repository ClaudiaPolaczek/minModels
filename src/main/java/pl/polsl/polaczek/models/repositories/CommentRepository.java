package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
