package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PortfolioDto {

    @NonNull
    private String username;

    @NonNull
    private String name;

    private String description;

    private String mainPhotoUrl;
}
