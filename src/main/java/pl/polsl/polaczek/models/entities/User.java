package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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

    private String mainPhotoUrl;

    private Double avgRate;

    @PrePersist
    private void setAvg() {
        avgRate = 0.0;
    }

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

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }
}
