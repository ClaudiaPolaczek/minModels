package pl.polsl.polaczek.models.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.polaczek.models.dao.ImageStore;
import pl.polsl.polaczek.models.entities.Image;

import javax.sql.DataSource;
import java.io.InputStream;

@Configuration
@ComponentScan("pl.polsl.polaczek.models.dao")
public class BeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
