package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photographer {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, length =  50)
    @Size(max = 50)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String country;

    @NotBlank
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String region;

    @NotBlank
    @Column(nullable = false, length = 16)
    @Size(min = 9, max = 16)
    private String phoneNumber;

    @Valid
    @OneToOne
    private User user;
}
