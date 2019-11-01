package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.CommentRepository;
import pl.polsl.polaczek.models.dao.NotificationRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.CommentDto;
import pl.polsl.polaczek.models.dto.NotificationDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.entities.Notification;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(final NotificationRepository notificationRepository, final UserRepository userRepository){
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<Notification> get(String username){

        userRepository.findById(username).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",username));

        return notificationRepository.findAllByUser_Username(username);
    }

    public Notification get(Long id){
        return notificationRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Notification","id",id.toString()));
    }

    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }

    public Notification add(@NonNull NotificationDto notificationDto){

        Notification notification = convertToEntity(notificationDto);
        return notificationRepository.save(notification);
    }

    private Notification convertToEntity(NotificationDto dto) {

        Notification notification = new Notification(userRepository.findById(dto.getUsername()).orElseThrow
                (() -> new BadRequestException("User", "id", dto.getUsername(), "does not exist")),
                dto.getContent());
        return notification;
    }

    public void delete(@NonNull Long id){
        notificationRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Notification","id",id.toString()));

        notificationRepository.deleteById(id);
    }

    public void read(@NonNull Long id){

        Notification notification = notificationRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Notification","id",id.toString()));

        if (notification.getReadValue() != 1) {
            notification.setReadValue(1);
        }

        notificationRepository.save(notification);
    }
}
