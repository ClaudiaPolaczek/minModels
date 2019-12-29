package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;
import java.time.LocalDateTime;

@Data
public class PhotoShootRegistrationDto {

    @NonNull
    private String invitingUserUsername;

    @NonNull
    private String invitedUserUsername;

    @NonNull
    private String city;

    @NonNull
    private String street;

    @NonNull
    private String houseNumber;

    @NonNull
    private String topic;

    private String notes;

    @NonNull
    private LocalDateTime meetingDate;

    @NonNull
    private Integer duration;
}
