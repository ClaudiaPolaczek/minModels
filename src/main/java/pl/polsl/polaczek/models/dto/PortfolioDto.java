package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PortfolioDto {

    @NotNull
    private String username;

    @NotNull
    private String name;

    private String description;

    private String mainPhotoUrl;
}
