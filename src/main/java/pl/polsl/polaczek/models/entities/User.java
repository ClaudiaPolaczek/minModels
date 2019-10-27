package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table( uniqueConstraints = { @UniqueConstraint(columnNames = "username")})
public class User{

    @Id
    @NonNull
    @NotBlank
    @Column(length = 64)
    private final String username;

    @NonNull
    @NotBlank
    @Column(length = 64)
    private String password;

    @NonNull
    private URole role;

    public URole getRole() {
        return role;
    }

    public void setRoles(URole roles) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
