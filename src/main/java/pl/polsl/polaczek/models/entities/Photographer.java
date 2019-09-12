package pl.polsl.polaczek.models.entities;

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
    private Set<PhotoShoot> photoShoots;

    @Valid
    @OneToOne
    private User user;

    @NonNull
    @NotBlank
    @Column(nullable = false, length =  64)
    @Size(max = 64)
    private String name;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String surname;

    @NonNull
    private Integer age;

    @NonNull
    private Character gender;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String country;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String city;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 16)
    @Size(min = 9, max = 16)
    private String phoneNumber;
}
