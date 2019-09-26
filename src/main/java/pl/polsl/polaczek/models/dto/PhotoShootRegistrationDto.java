package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

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
    private Duration duration;
}
