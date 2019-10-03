package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.CommentDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.services.CommentService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "comments", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CommentEndpoint {

    private final CommentService commentService;

    @Autowired
    CommentEndpoint(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{ratingUserUsername}/{ratedUserUsername}")
    public List<Comment> get(@PathVariable String ratingUserUsername, @PathVariable String ratedUserUsername){
        return commentService.get(ratingUserUsername, ratedUserUsername);
    }

    @GetMapping("/{id}")
    public Comment get(@PathVariable Long id){
        return commentService.get(id);
    }

    @GetMapping
    public List<Comment> getAll(){
        return commentService.getAll();
    }

    @GetMapping("/{ratingUserUsername}")
    public List<Comment> getAllCommentByRatingUser(@PathVariable String ratingUserUsername){
        return commentService.getCommentsByRatingUser(ratingUserUsername);
    }

    @GetMapping("/{ratedUserUsername}")
    public List<Comment> getAllCommentByRatedUser(@PathVariable String ratedUserUsername){
        return commentService.getCommentsByRatedUser(ratedUserUsername);
    }

    @PostMapping
    public Comment addComment(@Valid @RequestBody CommentDto dto) {
        return commentService.add(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.delete(id);
    }
}
