package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    private String content;

    private Integer readValue;

    private LocalDateTime addedDate;

    @PrePersist
    private void setRead() {
        readValue = 0;
        addedDate = LocalDateTime.now();
    }
}
