package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.PhotoShootRegistrationDto;
import pl.polsl.polaczek.models.entities.PhotoShoot;
import pl.polsl.polaczek.models.services.PhotoShootService;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "photoshoots", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PhotoShootEndpoint {

    private final PhotoShootService photoShootService;

    @Autowired
    PhotoShootEndpoint(PhotoShootService photoShootService) {
        this.photoShootService = photoShootService;
    }

    @GetMapping("/{id}")
    public PhotoShoot get(@PathVariable Long id){
        return photoShootService.get(id);
    }

    @GetMapping
    public List<PhotoShoot> getAll(){
        return photoShootService.getAll();
    }

//    @GetMapping("/m/{id}")
////    public List<PhotoShoot> getAllByModel(@PathVariable Long id){
////        return photoShootService.getAllByModel(id);
////    }
////
////    @GetMapping("/p/{id}")
////    public List<PhotoShoot> getAllByPhotographer(@PathVariable Long id){
////        return  photoShootService.getAllByPhotographer(id);
////    }

    @GetMapping("/inviting/{invitingUserUsername}")
    public List<PhotoShoot> getAllByInvitingUserUsername(@PathVariable String invitingUserUsername){
        return photoShootService.getAllByInvitingUserUsername(invitingUserUsername);
    }

    @GetMapping("/invited/{invitedUserUsername}")
    public List<PhotoShoot> getAllByInvitedUserUsername(@PathVariable String invitedUserUsername){
        return  photoShootService.getAllByInvitedUserUsername(invitedUserUsername);
    }

    @PostMapping
    public PhotoShoot registerPhotoShoot(@Valid @RequestBody PhotoShootRegistrationDto dto) {
        return photoShootService.register(dto);
    }

    @PatchMapping("/cancel/{id}")
    public void cancelPhotoShoot(@PathVariable Long id){
        photoShootService.cancel(id);
    }

    @PatchMapping("/accept/{id}")
    public void acceptPhotoShoot(@PathVariable Long id){
        photoShootService.accept(id);
    }

    @PatchMapping("/end/{id}")
    public void endPhotoShoot(@PathVariable Long id){
        photoShootService.end(id);
    }
}
