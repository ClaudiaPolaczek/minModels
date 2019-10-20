package pl.polsl.polaczek.models.services;

import org.assertj.core.util.Lists;
import org.dom4j.rule.Mode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.NewModelDto;
import pl.polsl.polaczek.models.dto.NewPhotographerDto;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelServiceTest {
    @Autowired
    private ModelService modelService;

    @MockBean
    private ModelRepository modelRepository;

    @MockBean
    private SurveyRepository surveyRepository;

    @MockBean
    private UserRepository userRepository;

    private User user = new User("Username", "Password", Role.PHOTOGRAPHER);

    private Survey survey = new Survey("First", "Last", 20, 'M',
            "Slaskie", "Cracow", "123456789");

    private Model model = new Model("blue", "brown", survey);

    private NewModelDto newModelDto = new NewModelDto();

    private static final Long NOT_EXISTING_MODEL_ID = 10L;
    private static final Long NOT_EXISTING_SURVEY_ID = 20L;
    private static final String NOT_EXISTING_USER_USERNAME = "Name";

    @Before
    public void setUpMocks(){
        initMocks(this);
        model.setId(1L);

        given(userRepository.findById(user.getUsername())).willReturn(Optional.of(user));
        given(surveyRepository.findById(survey.getId())).willReturn(Optional.of(survey));
        given(modelRepository.findById(model.getId())).willReturn(Optional.of(model));

        given(userRepository.findById(NOT_EXISTING_USER_USERNAME)).willReturn(Optional.empty());
        given(surveyRepository.findById(NOT_EXISTING_SURVEY_ID)).willReturn(Optional.empty());
        given(modelRepository.findById(NOT_EXISTING_MODEL_ID)).willReturn(Optional.empty());

        given(modelRepository.save(model)).willReturn(model);

        newModelDto.setUsername(user.getUsername());
        newModelDto.setPassword(user.getPassword());
        newModelDto.setAge(survey.getAge());
        newModelDto.setGender(survey.getGender());
        newModelDto.setFirstName(survey.getFirstName());
        newModelDto.setLastName(survey.getLastName());
        newModelDto.setRegion(survey.getRegion());
        newModelDto.setCity(survey.getCity());
        newModelDto.setPhoneNumber(survey.getPhoneNumber());
        newModelDto.setHairColor(model.getHairColor());
        newModelDto.setEyesColor(model.getEyesColor());
    }

    @Test
    public void shouldAddModel(){
        //given
        model.setId(null);

        //when
        Model created = modelService.add(newModelDto);

        //then
        assertThat(created).isEqualTo(model);
        verify(modelRepository).save(model);
    }

    @Test
    public void shouldAddSurvey(){
        //given
        survey.setId(null);

        //when
        Model model = modelService.add(newModelDto);

        //then
        verify(surveyRepository).save(survey);
    }

    @Test
    public void shouldDeleteModel() {
        //given
        //when
        modelService.delete(model.getId());

        //then
        verify(modelRepository).deleteById(model.getId());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToDeleteNonExistingModel() {
        //given
        //when
        modelService.delete(NOT_EXISTING_MODEL_ID);

        //then
        //expect exception
    }

    @Test
    public void shouldGetModel() {
        //given
        //when
        Model actual = modelService.get(model.getId());
        //then
        assertThat(actual).isEqualTo(model);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingModel() {
        //given
        //when
        modelService.get(NOT_EXISTING_MODEL_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllModels() {

        //given
        List<Model> models = Lists.newArrayList(
                new Model("blue","brown",survey),
                new Model("blue","brown", survey),
                new Model("blue","brown", survey)
        );

        given(modelRepository.findAll()).willReturn(models);

        //when
        List<Model> actual = modelService.getAll();

        //then
        assertThat(actual).containsExactlyElementsOf(models);
    }

    @Test
    public void shouldGetNoModels() {
        //given
        given(modelRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<Model> actual = modelService.getAll();
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldGetModelByUsername() {
        //given
        User user = new User("TestedUsername", "Password", Role.PHOTOGRAPHER);

        Survey survey = new Survey("First", "Last", 20, 'M',
                "Slaskie", "Cracow", "123456789");

        Model model = new Model("blue","brown",survey);
        model.setUser(user);

        given(modelRepository.findByUser_Username(user.getUsername())).willReturn(Optional.of(model));
        //when
        Model actual = modelService.get(user.getUsername());
        //then
        assertThat(actual).isEqualTo(model);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetModelByNonExistingUsername() {
        //given
        //when
        modelService.get(NOT_EXISTING_USER_USERNAME);
        //then
        //expect exception
    }
}
