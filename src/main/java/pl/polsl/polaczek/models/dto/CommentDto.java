package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentDto {

    @NonNull
    private String ratingUserUsername;

    @NonNull
    private String ratedUserUsername;

    @NonNull
    private Integer rating;

    private String content;
}
