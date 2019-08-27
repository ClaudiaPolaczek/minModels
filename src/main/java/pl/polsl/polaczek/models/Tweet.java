package pl.polsl.polaczek.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

    @Id
    @GeneratedValue
    @NonNull
    private Long id;

    @NonNull
    private String message;
}
