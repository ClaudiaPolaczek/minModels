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
    private Long modelId;

    @NotNull
    private Long photographerId;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String houseNumber;

    @NotNull
    @NotBlank
    private String topic;

    @NotNull
    private String notes;

    @NotNull
    private LocalDateTime meetingDate;

    @NotNull
    private Duration duration;
}
