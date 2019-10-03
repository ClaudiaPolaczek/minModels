package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dto.NewModelDto;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final UserService userService;
    private final SurveyService surveyService;
    private final PasswordEncoder passwordEncoder;
    private final SurveyRepository surveyRepository;

    @Autowired
    public ModelService(final ModelRepository modelRepository,
                               final UserService userService,
                               final SurveyService surveyService,
                               final SurveyRepository surveyRepository,
                               final PasswordEncoder passwordEncoder){
        this.modelRepository = modelRepository;
        this.userService = userService;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Model get(Long id){
        return modelRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Model", "id", id.toString()));
    }

    public Model get(String username){
        return modelRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Model", "username", username));
    }

    public List<Model> getAll(){
        return modelRepository.findAll();
    }

    public Model add(@NonNull NewModelDto dto){

        final User user = userService.create(dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()), Role.MODEL);

        if(dto.getGender() != 'M' && dto.getGender() != 'W')
            throw new BadRequestException("Model", "gender", dto.getGender().toString(),
                    "Model's gender should be either W or M");

        //final Survey survey = surveyService.add(dto);

        final Survey survey = new Survey(dto.getFirstName(), dto.getLastName(), dto.getAge(),
                dto.getGender(), dto.getCountry(), dto.getCity(), dto.getPhoneNumber());

        surveyRepository.save(survey);

        final Model model = new Model(dto.getEyesColor(), dto.getHairColor(), survey);
        model.setUser(user);

        return modelRepository.save(model);
    }

    public void delete(@NonNull Long id){

        modelRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Model","id",id.toString()));

        modelRepository.deleteById(id);
    }
}