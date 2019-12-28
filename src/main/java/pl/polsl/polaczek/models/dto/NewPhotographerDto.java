package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class NewPhotographerDto {

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

    private Integer regulationsAgreement;
}
