package pl.polsl.polaczek.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Column(length =  64)
    @Size(max = 64)
    private String title;

    @NonNull
    private String filePath;
}
