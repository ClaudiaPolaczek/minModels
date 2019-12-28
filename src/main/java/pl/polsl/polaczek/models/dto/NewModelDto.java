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

    private Integer birthdayYear;

    private Character gender;

    @NotBlank
    private String region;

    @NotBlank
    private String city;

    @NotBlank
    private String phoneNumber;

    private String hairColor;

    private String eyesColor;

    private Integer regulationsAgreement;
}
