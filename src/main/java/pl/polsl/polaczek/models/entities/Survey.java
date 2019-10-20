package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotBlank
    @Column(nullable = false, length =  64)
    @Size(max = 64)
    private String firstName;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String lastName;

    @NonNull
    private Integer age;

    @NonNull
    private Character gender;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 32)
    @Size(max = 32)
    private String region;

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
