package pl.polsl.polaczek.models.services;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.polsl.polaczek.models.dao.ModelRepository;
import pl.polsl.polaczek.models.dao.PhotoShootRepository;
import pl.polsl.polaczek.models.dao.PhotographerRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.PhotoShootRegistrationDto;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoShootServiceTest {

    @Autowired
    private PhotoShootService photoShootService;

    @MockBean
    private PhotoShootRepository photoShootRepository;

    @MockBean
    private ModelRepository modelRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PhotographerRepository photographerRepository;

    private Survey modelSurvey = new Survey("ModelName", "ModelSurname",20, 'W',
            "Country", "City", "123456789", 1);

    private Survey photographerSurvey = new Survey("ModelName", "ModelSurname",20, 'W',
            "Country", "City", "123456789", 1);

    //private Photographer photographer = new Photographer(photographerSurvey);

    private User userModel  = new User("model", "model", URole.MODEL);

    private User userPhotographer  = new User("photographer", "photographer", URole.PHOTOGRAPHER);

    private PhotoShoot photoShoot = new PhotoShoot(userPhotographer, userModel, PhotoShootStatus.CREATED, "topic", "notes",
            LocalDateTime.of(2020,1,30, 12, 0), 1,
            "City", "Street", "1");

    private PhotoShootRegistrationDto photoShootRegistrationDto = new PhotoShootRegistrationDto(
            userPhotographer.getUsername(), userModel.getUsername(),"City", "Street",
            "1","topic", LocalDateTime.of(2020,1,30, 12, 0), 1);

    private static final Long NOT_EXISTING_MODEL_ID = 10L;
    private static final Long NOT_EXISTING_PHOTOGRAPHER_ID = 20L;
    private static final Long NOT_EXISTING_PHOTOSHOOT_ID = 30L;
    private static final String NOT_EXISTING_USER_USERNAME = "nn";

    @Before
    public void setUpMocks(){
        initMocks(this);

        photoShoot.setId(3L);

        given(userRepository.findById(userModel.getUsername())).willReturn(Optional.of(userModel));
        given(userRepository.findById(userPhotographer.getUsername())).willReturn(Optional.of(userPhotographer));
        given(photoShootRepository.findById(photoShoot.getId())).willReturn(Optional.of(photoShoot));

        given(modelRepository.findById(NOT_EXISTING_MODEL_ID)).willReturn(Optional.empty());
        given(photographerRepository.findById(NOT_EXISTING_PHOTOGRAPHER_ID)).willReturn(Optional.empty());
        given(photoShootRepository.findById(NOT_EXISTING_PHOTOSHOOT_ID)).willReturn(Optional.empty());
        given(userRepository.findById(NOT_EXISTING_USER_USERNAME)).willReturn(Optional.empty());

        given(photoShootRepository.save(photoShoot)).willReturn(photoShoot);

        photoShootRegistrationDto.setInvitedUserUsername(userModel.getUsername());
        photoShootRegistrationDto.setInvitingUserUsername(userPhotographer.getUsername());
        photoShootRegistrationDto.setMeetingDate(photoShoot.getMeetingDate());
        photoShootRegistrationDto.setDuration(photoShoot.getDuration());
        photoShootRegistrationDto.setCity(photoShoot.getCity());
        photoShootRegistrationDto.setStreet(photoShoot.getStreet());
        photoShootRegistrationDto.setHouseNumber(photoShoot.getHouseNumber());
        photoShootRegistrationDto.setTopic(photoShoot.getTopic());
        photoShootRegistrationDto.setNotes(photoShoot.getNotes());
    }

    @Test
    public void shouldRegisterPhotoShoot(){
        //given
        photoShoot.setId(null);

        //when
        PhotoShoot created = photoShootService.register(photoShootRegistrationDto);

        //then
        assertThat(created).isEqualTo(photoShoot);
        verify(photoShootRepository).save(photoShoot);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotRegisterPhotoShootIfInvitingUserDoesNotExist() {
        //given
        photoShootRegistrationDto.setInvitingUserUsername(NOT_EXISTING_USER_USERNAME);

        //when
        photoShootService.register(photoShootRegistrationDto);

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotRegisterPhotoShootIfInvitedUserDoesNotExist() {
        //given
        photoShootRegistrationDto.setInvitedUserUsername(NOT_EXISTING_USER_USERNAME);

        //when
        photoShootService.register(photoShootRegistrationDto);

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotCreatePhotoShootIfPastDate() {
        //given
        photoShootRegistrationDto.setMeetingDate(LocalDateTime.of(1997, 9, 25, 15, 50));

        //when
        photoShootService.register(photoShootRegistrationDto);

        //then
        //expect exception
    }

    @Test
    public void shouldCancelPhotoShoot() {
        //given
        //when
        photoShootService.cancel(photoShoot.getId());

        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.CANCELED);
        verify(photoShootRepository).save(photoShoot);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToCancelNonExistingPhotoShoot() {
        //given
        //when
        photoShootService.cancel(NOT_EXISTING_PHOTOSHOOT_ID);

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotCancelPhotoShootIfEnd() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.END);

        //when
        photoShootService.cancel(photoShoot.getId());

        //then
        //expect exception
    }

    @Test
    public void shouldNotCancelButPass() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.CANCELED);

        //when
        photoShootService.cancel(photoShoot.getId());

        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.CANCELED);
        verify(photoShootRepository, never()).save(photoShoot);
    }

    @Test
    public void shouldAcceptPhotoShoot() {
        //given
        //when
        photoShootService.accept(photoShoot.getId());

        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.ACCEPTED);
        verify(photoShootRepository).save(photoShoot);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToAcceptNonExistingPhotoShoot() {
        //given
        //when
        photoShootService.accept(NOT_EXISTING_PHOTOSHOOT_ID);

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAcceptPhotoShootIfCanceled() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.CANCELED);

        //when
        photoShootService.accept(photoShoot.getId());

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAcceptPhotoShootIfEnd() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.END);

        //when
        photoShootService.accept(photoShoot.getId());

        //then
        //expect exception
    }

    @Test
    public void shouldNotAcceptButPass() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.ACCEPTED);

        //when
        photoShootService.accept(photoShoot.getId());

        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.ACCEPTED);
        verify(photoShootRepository, never()).save(photoShoot);
    }

    @Test
    public void shouldEndPhotoShoot() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.ACCEPTED);
        //when
        photoShootService.end(photoShoot.getId());
        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.END);
        verify(photoShootRepository).save(photoShoot);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotEndPhotoShootIfCanceled() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.CANCELED);

        //when
        photoShootService.end(photoShoot.getId());

        //then
        //expect exception
    }

    @Test
    public void shouldNotEndButPass() {
        //given
        photoShoot.setPhotoShootStatus(PhotoShootStatus.END);

        //when
        photoShootService.end(photoShoot.getId());

        //then
        assertThat(photoShoot.getPhotoShootStatus()).isEqualTo(PhotoShootStatus.END);
        verify(photoShootRepository, never()).save(photoShoot);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToEndNonExistingPhotoShoot() {
        //given
        //when
        photoShootService.end(NOT_EXISTING_PHOTOSHOOT_ID);
        //then
    }

    @Test
    public void shouldGetPhotoShoot() {
        //given
        photoShoot.setId(photoShoot.getId());
        //when
        PhotoShoot actual = photoShootService.get(photoShoot.getId());
        //then
        assertThat(actual).isEqualTo(photoShoot);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingPhotoShoot() {
        //given
        //when
        photoShootService.get(NOT_EXISTING_PHOTOSHOOT_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllPhotoShoots() {
        //given
        List<PhotoShoot> photoShoots = Lists.newArrayList(
                new PhotoShoot(userModel, userPhotographer, PhotoShootStatus.CREATED, "topic", "notes",
                        LocalDateTime.now(), 1,
                        "City", "Street", "1"),
                new PhotoShoot(userModel, userPhotographer, PhotoShootStatus.CANCELED, "topic", "notes",
                        LocalDateTime.now(), 1,
                        "City", "Street", "1"),
                new PhotoShoot(userModel, userPhotographer, PhotoShootStatus.END, "topic", "notes",
                        LocalDateTime.now(), 1,
                        "City", "Street", "1")
        );
        given(photoShootRepository.findAll()).willReturn(photoShoots);

        //when
        List<PhotoShoot> actual = photoShootService.getAll();

        //then
        assertThat(actual).containsExactlyElementsOf(photoShoots);
    }

    @Test
    public void shouldGetNoPhotoShoots() {
        //given
        given(photoShootRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<PhotoShoot> actual = photoShootService.getAll();
        //then
        assertThat(actual).isEmpty();
    }
}
