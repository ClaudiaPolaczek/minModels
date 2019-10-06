package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.entities.Role;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.BadRequestException;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }*/

    User create(String username, String password, Role role) {

       /* if(userRepository.findById(username) != null){
            throw new BadRequestException("User", "username", username, "already exist");
        }*/

        return userRepository.save(new User(username, password, role));
    }
}
