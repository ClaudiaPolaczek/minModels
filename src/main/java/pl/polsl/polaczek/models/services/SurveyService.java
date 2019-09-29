package pl.polsl.polaczek.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dto.NewUserDto;
import pl.polsl.polaczek.models.entities.Survey;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyService(final SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    Survey add(NewUserDto dto) {
        final Survey survey = new Survey(dto.getFirstName(), dto.getLastName(), dto.getAge(),
                dto.getGender(), dto.getCountry(), dto.getCity(), dto.getPhoneNumber());

        return surveyRepository.save(survey);
    }
}
