package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Data
public class ImageDto {

    private byte[] image;

    @NotNull
    private Long portfolioId;
}
