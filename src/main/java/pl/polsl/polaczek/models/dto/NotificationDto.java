package pl.polsl.polaczek.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotificationDto {

    @NotNull
    private String username;

    @NotNull
    private String content;
}
