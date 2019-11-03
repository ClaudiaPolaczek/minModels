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

    @NonNull
    private String fileUrl;
}
