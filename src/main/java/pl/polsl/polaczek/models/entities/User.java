package pl.polsl.polaczek.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class User{

    @Id
    @NotBlank
    @Size(max = 64)
    @Column(length = 64)
    private final String username;

    @JsonIgnore
    @NotBlank
    @Size(max = 64)
    @Column(length = 64)
    private String password;

    @NotNull
    @Column(nullable = false)
    private final Role role;

}
