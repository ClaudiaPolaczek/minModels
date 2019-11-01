package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;

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

    @PrePersist
    private void setRead() {
        readValue = 0;
    }
}
