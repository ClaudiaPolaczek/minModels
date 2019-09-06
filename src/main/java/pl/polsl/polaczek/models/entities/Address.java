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
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String city;

    @NotBlank
    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String street;

    @NotBlank
    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String houseNumber;
}
