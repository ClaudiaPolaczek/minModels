package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.NewUserDto;
import pl.polsl.polaczek.models.entities.Photographer;
import pl.polsl.polaczek.models.entities.Role;
import pl.polsl.polaczek.models.entities.Survey;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class PhotographerService {

    private final PhotographerRepository photographerRepository;
    private final UserService userService;
    private final SurveyService surveyService;
    private final PasswordEncoder passwordEncoder;

    private final SurveyRepository surveyRepository;

    @Autowired
    public PhotographerService(final PhotographerRepository photographerRepository,
                               final UserService userService,
                               final SurveyService surveyService,
                               final SurveyRepository surveyRepository,
                               final PasswordEncoder passwordEncoder){
        this.photographerRepository = photographerRepository;
        this.userService = userService;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Photographer get(Long id){
        return photographerRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Photographer", "id", id.toString()));
    }

    public Photographer get(String username){
        return photographerRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Photographer", "username", username));
    }

    public List<Photographer> getAll(){
        return photographerRepository.findAll();
    }

    public Photographer add(@NonNull NewUserDto dto){

        final User user = userService.create(dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()), Role.PHOTOGRAPHER);

        if(dto.getGender() != 'M' && dto.getGender() != 'W')
            throw new BadRequestException("Photographer", "gender", dto.getGender().toString(),
                    "Photographer's gender should be either W or M");

        //final Survey survey = surveyService.add(dto);

        final Survey survey = new Survey(dto.getFirstName(), dto.getLastName(), dto.getAge(),
                dto.getGender(), dto.getCountry(), dto.getCity(), dto.getPhoneNumber());

        surveyRepository.save(survey);

        final Photographer photographer = new Photographer(survey);
        photographer.setUser(user);

        return photographerRepository.save(photographer);
    }

    public void delete(@NonNull Long id){

        photographerRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Comment","id",id.toString()));

        photographerRepository.deleteById(id);
    }
}
