package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.NewModelDto;
import pl.polsl.polaczek.models.dto.UserEdit;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    @Autowired
    public ModelService(final ModelRepository modelRepository,
                               final UserDetailsServiceImpl userDetailsServiceImpl,
                               final SurveyRepository surveyRepository,
                               final PasswordEncoder passwordEncoder,
                                final UserRepository userRepository){
        this.modelRepository = modelRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.surveyRepository = surveyRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

        final User user = userDetailsServiceImpl.create(dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()), URole.MODEL);

        if(dto.getGender() != 'M' && dto.getGender() != 'W')
            throw new BadRequestException("Model", "gender", dto.getGender().toString(),
                    "Model's gender should be either W or M");

        final Survey survey = new Survey(dto.getFirstName(), dto.getLastName(), dto.getBirthdayYear(),
                dto.getGender(), dto.getRegion(), dto.getCity(), dto.getPhoneNumber(), dto.getRegulationsAgreement());

        surveyRepository.save(survey);

        final Model model = new Model(survey);
        model.setUser(user);

        return modelRepository.save(model);
    }

    public Model edit(String username, @NonNull UserEdit dto){

        Model model = modelRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Model", "username", username));

        Survey survey = model.getSurvey();
        User user = model.getUser();

        user.setPassword(dto.getPassword());
        survey.setRegion(dto.getRegion());
        survey.setCity(dto.getCity());
        survey.setPhoneNumber(dto.getPhoneNumber());
        survey.setBirthdayYear(dto.getBirthdayYear());

        model.setEyesColor(dto.getEyesColor());
        model.setHairColor(dto.getHairColor());

        surveyRepository.save(survey);
        userRepository.save(user);

        return modelRepository.save(model);
    }

    public Model editInstagramName(String username, @NonNull UserEdit dto){

        Model model = modelRepository.findByUser_Username(username)
                .orElseThrow(() -> new EntityDoesNotExistException("Model", "username", username));

        Survey survey = model.getSurvey();
        survey.setInstagramName(dto.getInstagramName());
        surveyRepository.save(survey);
        return modelRepository.save(model);
    }

    public void delete(@NonNull Long id){
        modelRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Model","id",id.toString()));

        modelRepository.deleteById(id);
    }
}
