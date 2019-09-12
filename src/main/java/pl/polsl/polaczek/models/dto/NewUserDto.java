package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String country;

    @NotBlank
    private String region;

    @NotBlank
    private String phoneNumber;
}
