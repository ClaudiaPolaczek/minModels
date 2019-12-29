package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.entities.URole;
import pl.polsl.polaczek.models.entities.User;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    User create(String username, String password, URole URole) {
        return userRepository.save(new User(username, password, URole));
    }
}
