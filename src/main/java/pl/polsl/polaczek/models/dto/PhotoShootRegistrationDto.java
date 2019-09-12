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

    private String city;
    private String street;
    private String houseNumber;

    private String topic;
    private String notes;


    @NotNull
    private LocalDateTime meetingDate;

    @NonNull
    private Duration duration;
}
