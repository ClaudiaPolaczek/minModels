package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.NewUserDto;
import pl.polsl.polaczek.models.entities.Photographer;
import pl.polsl.polaczek.models.services.PhotographerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "photographers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotographerEndpoint {

    private final PhotographerService photographerService;

    @Autowired
    PhotographerEndpoint(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    @GetMapping(value = "/{id}")
    public Photographer get(@PathVariable Long id){
        return photographerService.get(id);
    }

    @GetMapping(value = "/u/{username}")
    public Photographer get(@PathVariable String username){
        return photographerService.get(username);
    }

    @GetMapping
    public List<Photographer> getAll(){
        return photographerService.getAll();
    }

    @PostMapping
    public Photographer add(@Valid @RequestBody NewUserDto newPhotographer){
        return photographerService.add(newPhotographer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id){
        photographerService.delete(id);
    }
}
