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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class PhotoShootService {

    private final PhotoShootRepository photoShootRepository;
    private final PhotographerRepository photographerRepository;
    private final ModelRepository modelRepository;

    @Autowired
    public PhotoShootService(final PhotoShootRepository photoShootRepository, final PhotographerRepository photographerRepository, final ModelRepository modelRepository){
        this.photoShootRepository = photoShootRepository;
        this.photographerRepository = photographerRepository;
        this.modelRepository = modelRepository;
    }

    public PhotoShoot register(@NonNull PhotoShootRegistrationDto photoShootRegistrationDto){

        if(photoShootRegistrationDto.getMeetingDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Photo Shoot", "Meeting Date",
                    photoShootRegistrationDto.getMeetingDate().toString(), "need to be in future");
        }

        PhotoShoot photoShoot = convertToEntity(photoShootRegistrationDto);

        return photoShootRepository.save(photoShoot);
    }

    public void cancel(@NonNull Long meetingId){
        photoShootRepository.findById(meetingId).map(photoShoot -> {
            photoShoot.setPhotoShootStatus(PhotoShootStatus.CANCELED);
            return photoShoot;
        }).ifPresent(photoShootRepository::save);
    }

    private PhotoShoot convertToEntity(PhotoShootRegistrationDto dto) {

        PhotoShoot photoShoot = new PhotoShoot(photographerRepository.findById(dto.getPhotographerId()).orElseThrow(),
                modelRepository.findById(dto.getModelId()).orElseThrow(),
                dto.getTopic(),dto.getNotes(),dto.getMeetingDate(),dto.getDuration(), dto.getCity(),
                dto.getStreet(), dto.getHouseNumber());

        photoShoot.setPhotoShootStatus(PhotoShootStatus.CREATED);
        return photoShoot;
    }
}
