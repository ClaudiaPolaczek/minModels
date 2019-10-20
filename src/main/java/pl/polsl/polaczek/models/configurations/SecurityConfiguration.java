package pl.polsl.polaczek.models.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("photographer").password(encoder().encode("photographerPassword"))
                .roles("PHOTOGRAPHER")
                .and()
                .withUser("model").password(encoder().encode("modelPassword")).roles("MODEL");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/models/**").permitAll()
                .antMatchers(HttpMethod.GET, "/photographers").permitAll()
                .antMatchers("/comments/**").hasRole("MODEL")
                .anyRequest().hasRole("MODEL")
                .and()
                .formLogin().loginPage("http://localhost:8081/login").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
//                .cors()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable();

//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/foos").authenticated()
//                .antMatchers("/api/admin/**").hasRole("ADMIN")
//                .and()
//                .formLogin()
//                .successHandler(mySuccessHandler)
//                .failureHandler(myFailureHandler)
//                .and()
//                .logout();
    }

}
