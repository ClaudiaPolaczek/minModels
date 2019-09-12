package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PortfolioDto {

    private String name;
    private String description;

    @NonNull
    private Long userId;
}
