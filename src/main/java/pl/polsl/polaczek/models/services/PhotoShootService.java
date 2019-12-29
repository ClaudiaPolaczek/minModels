package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotoShootRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.PhotoShootRegistrationDto;
import pl.polsl.polaczek.models.entities.PhotoShoot;
import pl.polsl.polaczek.models.entities.PhotoShootStatus;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoShootService {

    private final PhotoShootRepository photoShootRepository;
    private final PhotographerRepository photographerRepository;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;

    @Autowired
    public PhotoShootService(final PhotoShootRepository photoShootRepository,
                             final PhotographerRepository photographerRepository,
                             final ModelRepository modelRepository,
                             final UserRepository userRepository){
        this.photoShootRepository = photoShootRepository;
        this.photographerRepository = photographerRepository;
        this.modelRepository = modelRepository;
        this.userRepository = userRepository;
    }

    public PhotoShoot get(Long id){
        return photoShootRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Photo Shoot","id",id.toString()));
    }

    public List<PhotoShoot> getAll(){
        return photoShootRepository.findAll();
    }

    public List<PhotoShoot> getAllByInvitingUserUsername(String invitingUserUsername){

        userRepository.findById(invitingUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",invitingUserUsername));

        List<PhotoShoot> photoShoots = photoShootRepository.findAllByInvitingUser_Username(invitingUserUsername);
        LocalDateTime now = LocalDateTime.now();

        for(PhotoShoot photoShoot : photoShoots){
            if(photoShoot.getMeetingDate().compareTo(now)<0){
                end(photoShoot.getId());
            }
        }
        return photoShoots;
    }

    public List<PhotoShoot> getAllForUser(String username){

        List<PhotoShoot> photoShoots = getAllByInvitedUserUsername(username);
        photoShoots.addAll(getAllByInvitingUserUsername(username));

        return photoShoots;
    }

    public List<PhotoShoot> getAllByInvitedUserUsername(String invitedUserUsername){

        userRepository.findById(invitedUserUsername).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",invitedUserUsername));

        List<PhotoShoot> photoShoots = photoShootRepository.findAllByInvitedUser_Username(invitedUserUsername);
        LocalDateTime now = LocalDateTime.now();

        for(PhotoShoot photoShoot : photoShoots){
            if(photoShoot.getMeetingDate().compareTo(now)<0){
                end(photoShoot.getId());
            }
        }

        return photoShoots;
    }

    public PhotoShoot register(@NonNull PhotoShootRegistrationDto photoShootRegistrationDto){

        PhotoShoot photoShoot = convertToEntity(photoShootRegistrationDto);
        return photoShootRepository.save(photoShoot);
    }

    private PhotoShoot convertToEntity(PhotoShootRegistrationDto dto) {

        return new PhotoShoot(userRepository.findById(dto.getInvitingUserUsername()).orElseThrow(()
                -> new BadRequestException("User", "username", dto.getInvitingUserUsername(), "does not exist")),
                userRepository.findById(dto.getInvitedUserUsername()).orElseThrow(()
                -> new BadRequestException("User", "username", dto.getInvitedUserUsername(), "does not exist")),
                PhotoShootStatus.CREATED,
                dto.getTopic(),dto.getNotes(),dto.getMeetingDate(),dto.getDuration(), dto.getCity(),
                dto.getStreet(), dto.getHouseNumber());
    }

    public void cancel(@NonNull Long photoShootId){
        PhotoShoot photoShoot =  photoShootRepository.findById(photoShootId).orElseThrow(()
                -> new EntityDoesNotExistException("Photo Shoot","id",photoShootId.toString()));

        if (photoShoot.getPhotoShootStatus() != PhotoShootStatus.CANCELED) {
            if (photoShoot.getPhotoShootStatus() == PhotoShootStatus.CREATED ||
                    photoShoot.getPhotoShootStatus() == PhotoShootStatus.ACCEPTED) {
                photoShoot.setPhotoShootStatus(PhotoShootStatus.CANCELED);
                photoShootRepository.save(photoShoot);
            } else throw new BadRequestException("PhotoShoot", "status", photoShoot.getPhotoShootStatus().toString(),
                    "should equal " + PhotoShootStatus.ACCEPTED.toString() + " or " + PhotoShootStatus.CREATED.toString());
        }
    }

    public void accept(@NonNull Long photoShootId){
        PhotoShoot photoShoot =  photoShootRepository.findById(photoShootId).orElseThrow(()
                -> new EntityDoesNotExistException("Photo Shoot","id",photoShootId.toString()));

        if (photoShoot.getPhotoShootStatus() != PhotoShootStatus.ACCEPTED) {
            if (photoShoot.getPhotoShootStatus() == PhotoShootStatus.CREATED) {
                photoShoot.setPhotoShootStatus(PhotoShootStatus.ACCEPTED);
                photoShootRepository.save(photoShoot);
            }
            else throw new BadRequestException("PhotoShoot", "status", photoShoot.getPhotoShootStatus().toString(),
                    "should equal " + PhotoShootStatus.CREATED.toString());
        }
    }

    public void end(@NonNull Long photoShootId) {

        PhotoShoot photoShoot = photoShootRepository.findById(photoShootId).orElseThrow(()
                -> new EntityDoesNotExistException("Photo Shoot", "id", photoShootId.toString()));

        if (photoShoot.getPhotoShootStatus() != PhotoShootStatus.END) {
            if (photoShoot.getPhotoShootStatus() == PhotoShootStatus.ACCEPTED) {
                photoShoot.setPhotoShootStatus(PhotoShootStatus.END);
                photoShootRepository.save(photoShoot);
            } else throw new BadRequestException("PhotoShoot", "status", photoShoot.getPhotoShootStatus().toString(),
                    "should equal " + PhotoShootStatus.ACCEPTED.toString());
        }
    }

    public void delete(@NonNull Long photoShootId) {
        photoShootRepository.deleteById(photoShootId);
    }
}
