package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.CommentRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.CommentDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public CommentService(final CommentRepository commentRepository, final UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> get( String ratingUserUsername , String ratedUserUsername){

        userRepository.findByUsername(ratingUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",ratingUserUsername));

        userRepository.findByUsername(ratedUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",ratedUserUsername));

        return commentRepository.findAllByRatingUserUsernameAndRatedUserUsername(ratingUserUsername,ratedUserUsername);
    }

    public Comment get(Long id){
        return commentRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Comment","id",id.toString()));
    }

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByRatingUser(String ratingUserUsername){

        userRepository.findByUsername(ratingUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",ratingUserUsername));

        return commentRepository.findAllByRatingUser_Username(ratingUserUsername);
    }

    public List<Comment> getCommentsByRatedUser(String ratedUserUsername){

        userRepository.findByUsername(ratedUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",ratedUserUsername));

        return commentRepository.findAllByRatedUser_Username(ratedUserUsername);
    }

    public Comment add(@NonNull CommentDto commentDto){

        Comment comment = convertToEntity(commentDto);
        return commentRepository.save(comment);
    }

    private Comment convertToEntity(CommentDto dto) {

       return new Comment(userRepository.findByUsername(dto.getRatingUserUsername()).orElseThrow
                (() -> new BadRequestException("User", "id", dto.getRatedUserUsername(), "does not exist")),
                userRepository.findByUsername(dto.getRatedUserUsername()).orElseThrow(()
                        -> new BadRequestException("User", "id", dto.getRatingUserUsername(), "does not exist")),
                dto.getRating(), dto.getContent());
    }

    public void delete(@NonNull Long id){
        Comment comment =  commentRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Comment","id",id.toString()));

        commentRepository.deleteById(id);
    }
}
