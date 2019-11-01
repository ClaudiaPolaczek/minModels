package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class PhotoShootRegistrationDto {

    @NotNull
    private String invitingUserUsername;

    @NotNull
    private String invitedUserUsername;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String houseNumber;

    @NotNull
    private String topic;

    @NotNull
    private String notes;

    @NotNull
    private LocalDateTime meetingDate;

    @NotNull
    private Integer duration;
}
