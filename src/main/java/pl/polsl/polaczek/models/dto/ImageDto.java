package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ImageDto {

    @NotNull
    private Long portfolioId;

    @NotNull
    private String fileUrl;

    @NotNull
    private String name;
}
