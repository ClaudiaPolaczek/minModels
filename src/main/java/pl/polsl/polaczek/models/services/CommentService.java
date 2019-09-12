package pl.polsl.polaczek.models.services;

import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(final CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
}
