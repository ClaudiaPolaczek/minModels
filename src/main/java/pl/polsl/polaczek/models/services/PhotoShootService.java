package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotoShootRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
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

    @Autowired
    public PhotoShootService(final PhotoShootRepository photoShootRepository,
                             final PhotographerRepository photographerRepository,
                             final ModelRepository modelRepository){
        this.photoShootRepository = photoShootRepository;
        this.photographerRepository = photographerRepository;
        this.modelRepository = modelRepository;
    }

    public PhotoShoot get(Long id){
        return photoShootRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Photo Shoot","id",id.toString()));
    }

    public List<PhotoShoot> getAll(){
        return photoShootRepository.findAll();
    }

    public List<PhotoShoot> getAllByModel(Long modelId){
        return photoShootRepository.findAllByModel_Id(modelId);
    }

    public List<PhotoShoot> getAllByPhotographer(Long photographerId){
        return photoShootRepository.findAllByPhotographer_Id(photographerId);
    }

    public PhotoShoot register(@NonNull PhotoShootRegistrationDto photoShootRegistrationDto){

       /* if(photoShootRegistrationDto.getMeetingDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Photo Shoot", "Meeting Date",
                    photoShootRegistrationDto.getMeetingDate().toString(), "need to be in future");
        }*/

        PhotoShoot photoShoot = convertToEntity(photoShootRegistrationDto);
        return photoShootRepository.save(photoShoot);
    }

    private PhotoShoot convertToEntity(PhotoShootRegistrationDto dto) {

        PhotoShoot photoShoot = new PhotoShoot(photographerRepository.findById(dto.getPhotographerId()).orElseThrow(()
                -> new BadRequestException("Photographer", "id", dto.getPhotographerId().toString(), "does not exist")),
                modelRepository.findById(dto.getModelId()).orElseThrow(()
                -> new BadRequestException("Model", "id", dto.getModelId().toString(), "does not exist")),
                PhotoShootStatus.CREATED,
                dto.getTopic(),dto.getNotes(),dto.getMeetingDate(),dto.getDuration(), dto.getCity(),
                dto.getStreet(), dto.getHouseNumber());

        return photoShoot;
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
        /*photoShootRepository.findById(photoShootId).map(photoShoot -> {
            photoShoot.setPhotoShootStatus(PhotoShootStatus.ACCEPTED);
            return photoShoot;
        }).ifPresent(photoShootRepository::save);*/

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
}
