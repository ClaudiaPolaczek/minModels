package pl.polsl.polaczek.models.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @NotBlank
    @Column(length =  64)
    @Size(min = 3, max = 64)
    private String name;

    @Column(length = 800)
    @Size(max = 800)
    private String description;

    private String mainPhotoUrl;

    @Column(nullable = false)
    private LocalDateTime addedDate;

    @PrePersist
    private void setDate() {
        addedDate = LocalDateTime.now();
    }
}
