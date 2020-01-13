package pl.polsl.polaczek.models.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photographer {

    @Id
    @GeneratedValue
    private Long id;

    @Valid
    @OneToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Survey survey;
}
