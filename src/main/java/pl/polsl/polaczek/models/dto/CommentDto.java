package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentDto {

    private Integer rating;
    private String content;

    @NonNull
    private Long userId;
}
