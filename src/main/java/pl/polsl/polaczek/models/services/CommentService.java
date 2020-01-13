package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.CommentRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.CommentDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.entities.URole;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(final CommentRepository commentRepository, final UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> get( String ratingUserUsername , String ratedUserUsername){
        userRepository.findById(ratingUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",ratingUserUsername));
        userRepository.findById(ratedUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",ratedUserUsername));
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
        userRepository.findById(ratingUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",ratingUserUsername));
        return commentRepository.findAllByRatingUser_Username(ratingUserUsername);
    }

    public List<Comment> getCommentsByRatedUser(String ratedUserUsername){
        userRepository.findById(ratedUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",ratedUserUsername));
        return commentRepository.findAllByRatedUser_Username(ratedUserUsername);
    }

    public Comment add(@NonNull CommentDto commentDto){
        Comment comment = convertToEntity(commentDto);
        return commentRepository.save(comment);
    }

    public List<User> getAvgOfRating(){

        List<User> users = userRepository.findAll();
        Double sum;
        Double avg;

        for(User user: users){
            if(user.getRole()!= URole.ADMIN) {
                List<Comment> comments = commentRepository.findAllByRatedUser_Username(user.getUsername());
                if(comments.size()!=0) {
                    sum = 0.0;
                    for (Comment comment : comments) {
                        sum += comment.getRating();
                    }
                    avg = sum / comments.size();
                    user.setAvgRate(avg);
                    userRepository.save(user);
                }
            }
        }
        return users;
    }

    private Comment convertToEntity(CommentDto dto) {

        User user = userRepository.findById(dto.getRatedUserUsername()).orElseThrow(()
                -> new BadRequestException("User", "id", dto.getRatingUserUsername(), "does not exist"));

        Comment comment = new Comment(userRepository.findById(dto.getRatingUserUsername()).orElseThrow
                (() -> new BadRequestException("User", "id", dto.getRatedUserUsername(), "does not exist")), user,
                dto.getRating());

        comment.setContent(dto.getContent());
        return comment;
    }

    public void delete(@NonNull Long id){
        commentRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Comment","id",id.toString()));

        commentRepository.deleteById(id);
    }
}
