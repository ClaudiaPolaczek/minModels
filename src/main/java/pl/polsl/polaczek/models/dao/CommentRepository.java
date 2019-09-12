package pl.polsl.polaczek.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.polaczek.models.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
