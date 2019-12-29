package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImageDto {

    @NonNull
    private Long portfolioId;

    @NonNull
    private String fileUrl;

    @NonNull
    private String name;
}
