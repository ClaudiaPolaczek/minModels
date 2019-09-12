package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @ManyToOne
    private Portfolio portfolio;

    @Lob
    @Column(name="IMAGE", nullable=false, columnDefinition="mediumblob")
    private byte[] image;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime addedDate;

    @PrePersist
    private void setDate() {
        addedDate = LocalDateTime.now();
    }
}
