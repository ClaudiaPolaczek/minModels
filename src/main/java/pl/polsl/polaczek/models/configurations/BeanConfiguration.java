package pl.polsl.polaczek.models.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.polsl.polaczek.models.dao.ImageStore;
import pl.polsl.polaczek.models.entities.Image;

import java.io.InputStream;

@Configuration
@ComponentScan("pl.polsl.polaczek.models.dao")
public class BeanConfiguration {

   // private final UserService userService;

   /* @Autowired
    public BeanConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

    @Bean
    public ImageStore imageStore() {
        return new ImageStore() {
            @Override
            public void setContent(Image property, InputStream content) {

            }

            @Override
            public void unsetContent(Image property) {

            }

            @Override
            public InputStream getContent(Image property) {
                return null;
            }
        };
    }
}
