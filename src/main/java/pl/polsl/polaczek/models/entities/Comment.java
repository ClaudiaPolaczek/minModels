package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private Integer rating;

    @NonNull
    @Lob
    private String content;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    private LocalDateTime  addedDate;

    @PrePersist
    private void setDate() {
        addedDate = LocalDateTime.now();
    }
}
