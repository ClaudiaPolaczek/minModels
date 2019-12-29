package pl.polsl.polaczek.models.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewAdmin {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
