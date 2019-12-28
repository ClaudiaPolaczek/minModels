package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.NewModelDto;
import pl.polsl.polaczek.models.dto.UserEdit;
import pl.polsl.polaczek.models.entities.Model;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;
import pl.polsl.polaczek.models.services.ModelService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "models", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModelEndpoint {

    private final ModelService modelService;

    @Autowired
    ModelEndpoint(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/{id}")
    public Model get(@PathVariable Long id){
        return modelService.get(id);
    }

    @GetMapping(value = "/u/{username}")
    public Model get(@PathVariable String username){
        return modelService.get(username);
    }

    @GetMapping
    public List<Model> getAll(){
        return modelService.getAll();
    }

    @PatchMapping("/{username}")
    public Model editModel(@PathVariable String username, @Valid @RequestBody UserEdit newModelDto){
            return modelService.edit(username, newModelDto);
    }

    @PatchMapping("/instagram/{username}")
    public Model editInstagramName(@PathVariable String username, @Valid @RequestBody UserEdit newModelDto){
        return modelService.editInstagramName(username, newModelDto);
    }

    @PostMapping
    public Model add(@Valid @RequestBody NewModelDto newModelDto){
        return modelService.add(newModelDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteModel(@PathVariable Long id){
        modelService.delete(id);
    }
}
