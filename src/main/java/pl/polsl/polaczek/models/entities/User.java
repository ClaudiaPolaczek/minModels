package pl.polsl.polaczek.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class User{

    @Id
    @NonNull
    @Getter
    @NotBlank
    @Column(length = 64)
    private final String username;

    @NonNull
    @NotBlank
    @Setter
    @Getter
    @Column(length = 64)
    private String password;

    @NonNull
    private final Role role;

    public Role getRole() {
        return role;
    }

//    @JsonIgnore
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Sets.newHashSet(new SimpleGrantedAuthority("ROLE_" + role.toString()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
