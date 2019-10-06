package pl.polsl.polaczek.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photographer {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "photographer")
    @JsonIgnore
    private Set<PhotoShoot> photoShoots;

    @Valid
    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Survey survey;
}
