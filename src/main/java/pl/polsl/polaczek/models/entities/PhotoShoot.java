package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoShoot {

    @Id
    @GeneratedValue
    private Long id;

    @Valid
    @NonNull
    @ManyToOne
    private Photographer photographer;

    @Valid
    @NonNull
    @ManyToOne
    private Model model;

    @NonNull
    @Column(nullable = false)
    private PhotoShootStatus photoShootStatus;

    @NonNull
    @NotBlank
    @Column(nullable = false, length =  64)
    @Size(max = 64)
    private String topic;

    @NonNull
    @Lob
    @Column(length = 8000)
    @Size(max = 8000)
    private String notes;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime meetingDate;

    @NonNull
    @Column(nullable = false)
    private Duration duration;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String city;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 120)
    @Size(max = 120)
    private String street;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String houseNumber;
}
