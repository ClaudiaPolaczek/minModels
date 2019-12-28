package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.NewPhotographerDto;
import pl.polsl.polaczek.models.dto.UserEdit;
import pl.polsl.polaczek.models.entities.Photographer;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;
import pl.polsl.polaczek.models.services.PhotographerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
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

    @PatchMapping("/{username}")
    public Photographer editPhotographer(@PathVariable String username, @Valid @RequestBody UserEdit dto){
        return photographerService.edit(username, dto);
    }

    @PatchMapping("/instagram/{username}")
    public Photographer editInstagramName(@PathVariable String username, @Valid @RequestBody UserEdit dto){
        return photographerService.editInstagramName(username, dto);
    }

    @PostMapping
    public Photographer add(@Valid @RequestBody NewPhotographerDto newPhotographer){
        return photographerService.add(newPhotographer);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePhotographer(@PathVariable Long id){
        photographerService.delete(id);
    }
}
