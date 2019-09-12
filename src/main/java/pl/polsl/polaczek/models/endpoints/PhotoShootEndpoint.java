package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.polaczek.models.dto.PhotoShootRegistrationDto;
import pl.polsl.polaczek.models.entities.PhotoShoot;
import pl.polsl.polaczek.models.services.PhotoShootService;

import javax.validation.Valid;

@RestController
public class PhotoShootEndpoint {

    private final PhotoShootService photoShootService;

    @Autowired
    PhotoShootEndpoint(PhotoShootService visitService) {
        this.photoShootService = visitService;
    }

    public PhotoShoot registerPhotoShoot(@Valid @RequestBody PhotoShootRegistrationDto dto) {
        return photoShootService.register(dto);
    }
}
