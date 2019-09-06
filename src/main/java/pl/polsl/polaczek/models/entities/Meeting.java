package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    private Long reward;

    @NotBlank
    @Column(nullable = false, length =  64)
    @Size(max = 64)
    private String subject;

    @Column(length = 8000)
    @Size(max = 8000)
    private String notes;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime meetingDate;

    @PrePersist
    private void setDate() {
        meetingDate = LocalDateTime.now();
    }

    @NonNull
    @ManyToOne
    private Photographer model;

    @NonNull
    @ManyToOne
    private Model photographer;

    @OneToOne
    private Address address;

    @NotNull
    @Column(nullable = false)
    private MeetingStatus meetingStatus;
}