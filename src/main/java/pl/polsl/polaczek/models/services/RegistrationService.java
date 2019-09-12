package pl.polsl.polaczek.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;

@Service
public class RegistrationService {

    private final ModelRepository modelRepository;
    private final PhotographerRepository photographerRepository;

    @Autowired
    public RegistrationService(final ModelRepository modelRepository,
                               final PhotographerRepository photographerRepository){
        this.modelRepository = modelRepository;
        this.photographerRepository = photographerRepository;
    }

}
