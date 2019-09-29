package pl.polsl.polaczek.models.services;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.SurveyRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.NewUserDto;
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
public class PhotographerServiceTest {

    @Autowired
    private PhotographerService photographerService;

    @MockBean
    private PhotographerRepository photographerRepository;

    @MockBean
    private SurveyRepository surveyRepository;

    @MockBean
    private UserRepository userRepository;


    private User user = new User("Username", "Password", Role.PHOTOGRAPHER);

    private Survey survey = new Survey("First", "Last", 20, 'M',
            "Poland", "Cracow", "123456789");

    private Photographer photographer = new Photographer(survey);

    private NewUserDto newUserDto = new NewUserDto();

    private static final Long NOT_EXISTING_PHOTOGRAPHER_ID = 10L;
    private static final Long NOT_EXISTING_SURVEY_ID = 20L;
    private static final String NOT_EXISTING_USER_USERNAME = "Name";

    @Before
    public void setUpMocks(){
        initMocks(this);
        photographer.setId(1L);

        given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));
        given(surveyRepository.findById(survey.getId())).willReturn(Optional.of(survey));
        given(photographerRepository.findById(photographer.getId())).willReturn(Optional.of(photographer));

        given(userRepository.findByUsername(NOT_EXISTING_USER_USERNAME)).willReturn(Optional.empty());
        given(surveyRepository.findById(NOT_EXISTING_SURVEY_ID)).willReturn(Optional.empty());
        given(photographerRepository.findById(NOT_EXISTING_PHOTOGRAPHER_ID)).willReturn(Optional.empty());

        given(photographerRepository.save(photographer)).willReturn(photographer);

        newUserDto.setUsername(user.getUsername());
        newUserDto.setPassword(user.getPassword());
        newUserDto.setAge(survey.getAge());
        newUserDto.setGender(survey.getGender());
        newUserDto.setFirstName(survey.getFirstName());
        newUserDto.setLastName(survey.getLastName());
        newUserDto.setCountry(survey.getCountry());
        newUserDto.setCity(survey.getCity());
        newUserDto.setPhoneNumber(survey.getPhoneNumber());
    }

    @Test
    public void shouldAddPhotographer(){
        //given
        photographer.setId(null);

        //when
        Photographer created = photographerService.add(newUserDto);

        //then
        assertThat(created).isEqualTo(photographer);
        verify(photographerRepository).save(photographer);
    }

    @Test
    public void shouldAddSurvey(){
        //given
        survey.setId(null);

        //when
        Photographer photographer = photographerService.add(newUserDto);

        //then
        verify(surveyRepository).save(survey);
    }

    @Test
    public void shouldDeletePhotographer() {
        //given
        //when
        photographerService.delete(photographer.getId());

        //then
        verify(photographerRepository).deleteById(photographer.getId());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToDeleteNonExistingPhotographer() {
        //given
        //when
        photographerService.delete(NOT_EXISTING_PHOTOGRAPHER_ID);

        //then
        //expect exception
    }

    @Test
    public void shouldGetPhotographer() {
        //given
        //when
        Photographer actual = photographerService.get(photographer.getId());
        //then
        assertThat(actual).isEqualTo(photographer);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingPhotographer() {
        //given
        //when
        photographerService.get(NOT_EXISTING_PHOTOGRAPHER_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllPhotographers() {

        //given
        List<Photographer> photographers = Lists.newArrayList(
                new Photographer(survey),
                new Photographer(survey),
                new Photographer(survey)
        );


        given(photographerRepository.findAll()).willReturn(photographers);

        //when
        List<Photographer> actual = photographerService.getAll();

        //then
        assertThat(actual).containsExactlyElementsOf(photographers);
    }

    @Test
    public void shouldGetNoPhotographers() {
        //given
        given(photographerRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<Photographer> actual = photographerService.getAll();
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldGetPhotographerByUsername() {
        //given
        User user = new User("TestedUsername", "Password", Role.PHOTOGRAPHER);

        Survey survey = new Survey("First", "Last", 20, 'M',
                "Poland", "Cracow", "123456789");

        Photographer photographer = new Photographer(survey);
        photographer.setUser(user);

        given(photographerRepository.findByUser_Username(user.getUsername())).willReturn(Optional.of(photographer));
        //when
        Photographer actual = photographerService.get(user.getUsername());
        //then
        assertThat(actual).isEqualTo(photographer);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetPhotographerByNonExistingUsername() {
        //given
        //when
        photographerService.get(NOT_EXISTING_USER_USERNAME);
        //then
        //expect exception
    }
}
