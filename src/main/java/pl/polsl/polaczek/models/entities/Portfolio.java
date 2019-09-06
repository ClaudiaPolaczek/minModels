package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, length =  64)
    @Size(max = 64)
    private String name;

    @Column(length = 8000)
    @Size(max = 8000)
    private String description;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime addedDate;

    @PrePersist
    private void setDate() {
        addedDate = LocalDateTime.now();
    }

    @NonNull
    @ManyToOne
    private User user;
}
