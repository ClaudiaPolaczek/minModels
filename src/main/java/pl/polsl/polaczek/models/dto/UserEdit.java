package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserEdit {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Integer age;

    private Character gender;

    private String region;

    private String city;

    private String phoneNumber;

    private String hairColor;

    private String eyesColor;
}
