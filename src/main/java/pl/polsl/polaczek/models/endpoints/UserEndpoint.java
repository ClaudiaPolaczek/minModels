package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;
import pl.polsl.polaczek.models.services.CommentService;
import pl.polsl.polaczek.models.services.PhotoShootService;
import pl.polsl.polaczek.models.services.PortfolioService;
import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserEndpoint {

    private final UserRepository userRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PhotoShootService photoShootService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username){
        return userRepository.findById(username)
                .orElseThrow(() -> new EntityDoesNotExistException("User", "username", username));
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PatchMapping("/photo/{username}")
    public User addMainPhotoUrl(@PathVariable String username, @Valid @RequestBody ImageDto dto){

        User user = userRepository.findById(username).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",username));
        user.setMainPhotoUrl(dto.getFileUrl());
        return userRepository.save(user);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable String username){
        User user = userRepository.findById(username).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",username));

        List<Comment> commentsByRatedUser = commentService.getCommentsByRatedUser(username);

        for(Comment comment: commentsByRatedUser){
            commentService.delete(comment.getId());
        }

        List<Comment> commentsByRatingUser = commentService.getCommentsByRatingUser(username);

        for(Comment comment: commentsByRatingUser){
            commentService.delete(comment.getId());
        }

        List<PhotoShoot> allByInvitedUserUsername = photoShootService.getAllByInvitedUserUsername(username);

        for(PhotoShoot photoShoot: allByInvitedUserUsername){
            photoShootService.delete(photoShoot.getId());
        }

        List<PhotoShoot> allByInvitingUserUsername = photoShootService.getAllByInvitingUserUsername(username);

        for(PhotoShoot photoShoot: allByInvitingUserUsername){
            photoShootService.delete(photoShoot.getId());
        }

        List<Portfolio> allPortfoliosByUser = portfolioService.getAllPortfoliosByUser(username);

        for(Portfolio portfolio: allPortfoliosByUser){
            portfolioService.deletePortfolio(portfolio.getId());
        }

        if(user.getRole() == URole.MODEL){
            Model model = modelRepository.findByUser_Username(username).orElseThrow(()
                    -> new EntityDoesNotExistException("Model","username",username));

            modelRepository.delete(model);

        } else if (user.getRole() == URole.PHOTOGRAPHER){
            Photographer photographer = photographerRepository.findByUser_Username(username).orElseThrow(()
                    -> new EntityDoesNotExistException("Photographer","username",username));

            photographerRepository.delete(photographer);
        }

        userRepository.deleteById(username);
    }

    @DeleteMapping("/delete/u/{username}")
    public void deleteUserWithNoRole(@PathVariable String username){

        userRepository.findById(username).orElseThrow(()
                -> new EntityDoesNotExistException("User","id",username));

        userRepository.deleteById(username);
    }
}
