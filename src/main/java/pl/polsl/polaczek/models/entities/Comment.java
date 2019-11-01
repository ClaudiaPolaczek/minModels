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
    @ManyToOne
    private User ratingUser;

    @NonNull
    @ManyToOne
    private User ratedUser;

    @NonNull
    private Integer rating;
    
    @Lob
    private String content;

    @Column(nullable = false)
    private LocalDateTime addedDate;

    @PrePersist
    private void setDate() {
        addedDate = LocalDateTime.now();
    }
}
