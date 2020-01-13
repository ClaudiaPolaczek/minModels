package pl.polsl.polaczek.models.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Model {

    @Id
    @GeneratedValue
    private Long id;

    @Valid
    @OneToOne
    private User user;

    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String eyesColor;

    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String hairColor;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Survey survey;

    @PrePersist
    private void setHairAndEyesColor() {
        hairColor = "-";
        eyesColor = "-";
    }
}
