package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class NewModelDto {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private Integer birthdayYear;

    private Character gender;

    @NonNull
    private String region;

    @NonNull
    private String city;

    @NonNull
    private String phoneNumber;

    private String hairColor;

    private String eyesColor;

    private Integer regulationsAgreement;
}
