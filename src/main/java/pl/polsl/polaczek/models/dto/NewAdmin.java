package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class NewAdmin {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
