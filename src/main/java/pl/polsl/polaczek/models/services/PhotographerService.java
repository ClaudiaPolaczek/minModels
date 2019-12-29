package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.NewPhotographerDto;
import pl.polsl.polaczek.models.dto.UserEdit;
import pl.polsl.polaczek.models.entities.Photographer;
import pl.polsl.polaczek.models.entities.URole;
import pl.polsl.polaczek.models.entities.Survey;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class PhotographerService {

    private final PhotographerRepository photographerRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SurveyService surveyService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;

    @Autowired
    public PhotographerService(final PhotographerRepository photographerRepository,
                               final UserDetailsServiceImpl userDetailsServiceImpl,
                               final SurveyService surveyService,
                               final SurveyRepository surveyRepository,
                               final PasswordEncoder passwordEncoder,
                               final UserRepository userRepository){
        this.photographerRepository = photographerRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

    public Photographer add(@NonNull NewPhotographerDto dto){

        final User user = userDetailsServiceImpl.create(dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()), URole.PHOTOGRAPHER);

        if(dto.getGender() != 'M' && dto.getGender() != 'W')
            throw new BadRequestException("Photographer", "gender", dto.getGender().toString(),
                    "Photographer's gender should be either W or M");

        final Survey survey = new Survey(dto.getFirstName(), dto.getLastName(), dto.getBirthdayYear(),
                dto.getGender(), dto.getRegion(), dto.getCity(), dto.getPhoneNumber(), dto.getRegulationsAgreement());

        surveyRepository.save(survey);

        final Photographer photographer = new Photographer(survey);
        photographer.setUser(user);

        return photographerRepository.save(photographer);
    }

    public Photographer edit(String username, @NonNull UserEdit dto){

        Photographer photographer = photographerRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Photographer", "username", username));

        Survey survey = photographer.getSurvey();
        User user = photographer.getUser();

        user.setPassword(dto.getPassword());
        survey.setRegion(dto.getRegion());
        survey.setCity(dto.getCity());
        survey.setPhoneNumber(dto.getPhoneNumber());
        survey.setBirthdayYear(dto.getBirthdayYear());

        surveyRepository.save(survey);
        userRepository.save(user);

        return photographerRepository.save(photographer);
    }

    public Photographer editInstagramName(String username, @NonNull UserEdit dto){

        Photographer photographer = photographerRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Photographer", "username", username));

        Survey survey = photographer.getSurvey();
        survey.setInstagramName(dto.getInstagramName());
        surveyRepository.save(survey);
        return photographerRepository.save(photographer);
    }

    public void delete(@NonNull Long id){

        photographerRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Photographer","id",id.toString()));

        photographerRepository.deleteById(id);
    }
}
