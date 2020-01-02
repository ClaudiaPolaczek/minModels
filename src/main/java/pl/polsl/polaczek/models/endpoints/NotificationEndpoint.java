package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.NotificationDto;
import pl.polsl.polaczek.models.entities.Notification;
import pl.polsl.polaczek.models.services.NotificationService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class NotificationEndpoint {

    private final NotificationService notificationService;

    @Autowired
    NotificationEndpoint(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{username}")
    public List<Notification> getByUsers(@PathVariable String username){
        return notificationService.get(username);
    }

    @GetMapping("/n/{username}")
    public List<Notification> getNonReadByUsers(@PathVariable String username){
        return notificationService.getNonRead(username);
    }

    @GetMapping("/id/{id}")
    public Notification getById(@PathVariable Long id){
        return notificationService.get(id);
    }

    @GetMapping
    public List<Notification> getAll(){
        return notificationService.getAll();
    }

    @PostMapping
    public Notification addNotification(@Valid @RequestBody NotificationDto dto) {
        return notificationService.add(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNotification(@PathVariable Long id){
        notificationService.delete(id);
    }

    @PatchMapping("/read/{id}")
    public void readNotification(@PathVariable Long id){
        notificationService.read(id);
    }

}
