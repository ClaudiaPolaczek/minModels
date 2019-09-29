package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserEndpoint {

    private final UserRepository userRepository;

    @Autowired
    UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public User getUserDetails(@PathVariable String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityDoesNotExistException("User", "username", username));
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
