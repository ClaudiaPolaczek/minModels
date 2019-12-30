package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhotoShoot {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private User invitingUser;

    @NonNull
    @ManyToOne
    private User invitedUser;

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
    @Column(length = 800)
    @Size(max = 800)
    private String notes;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime meetingDate;

    @NonNull
    @Column(nullable = false)
    private Integer duration;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 128)
    @Size(max = 128)
    private String city;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 128)
    @Size(max = 128)
    private String street;

    @NonNull
    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(max = 64)
    private String houseNumber;
}
