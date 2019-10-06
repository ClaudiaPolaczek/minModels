package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewModelDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Integer age;

    private Character gender;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String hairColor;

    @NotBlank
    private String eyesColor;
}
