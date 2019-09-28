package pl.polsl.polaczek.models.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    @NotNull
    private String ratingUserUsername;

    @NotNull
    private String ratedUserUsername;

    @NotNull
    private Integer rating;

    private String content;
}
