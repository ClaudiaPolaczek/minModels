package pl.polsl.polaczek.models.services;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.polsl.polaczek.models.dao.ImageRepository;
import pl.polsl.polaczek.models.dao.ImageStore;
import pl.polsl.polaczek.models.dao.PortfolioRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.dto.PortfolioDto;
import pl.polsl.polaczek.models.entities.*;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PortfolioServiceTest {

    @Autowired
    private PortfolioService portfolioService;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private PortfolioRepository portfolioRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ImageStore imageStore;


    private User user = new User("Username", "Password", Role.PHOTOGRAPHER);

    private Portfolio portfolio= new Portfolio(user, "name");

    private Image image = new Image(portfolio);

    private PortfolioDto portfolioDto = new PortfolioDto();
    private ImageDto imageDto = new ImageDto();
    private String imageInput = "C:\\Users\\Claudia\\Desktop\\_DSC9178.JPG";


    private static final Long NOT_EXISTING_IMAGE_ID = 10L;
    private static final Long NOT_EXISTING_PORTFOLIO_ID = 10L;
    private static final String NOT_EXISTING_USER_USERNAME = "User";

    @Before
    public void setUpMocks(){
        initMocks(this);
        image.setId(1L);
        portfolio.setId(1L);

        given(portfolioRepository.findById(portfolio.getId())).willReturn(Optional.of(portfolio));
        given(imageRepository.findById(image.getId())).willReturn(Optional.of(image));
        given(userRepository.findById(user.getUsername())).willReturn(Optional.of(user));


        given(portfolioRepository.findById(NOT_EXISTING_PORTFOLIO_ID)).willReturn(Optional.empty());
        given(imageRepository.findById(NOT_EXISTING_IMAGE_ID)).willReturn(Optional.empty());
        given(userRepository.findById(NOT_EXISTING_USER_USERNAME)).willReturn(Optional.empty());

        given(portfolioRepository.save(portfolio)).willReturn(portfolio);
        given(imageRepository.save(image)).willReturn(image);
       // given(imageStore.getContent(image)).willReturn(imageInput);

        portfolioDto.setDescription(portfolio.getDescription());
        portfolioDto.setName(portfolio.getName());
        portfolioDto.setUsername(user.getUsername());

        imageDto.setTitle(image.getTitle());
        imageDto.setFilePath(imageInput);
        imageDto.setPortfolioId(portfolio.getId());
    }

    @Test
    public void shouldAddPortfolio(){
        //given
        portfolio.setId(null);

        //when
        Portfolio added = portfolioService.addPortfolio(portfolioDto);

        //then
        assertThat(added).isEqualTo(portfolio);
        verify(portfolioRepository).save(portfolio);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddPortfolioIfUserDoesNotExist() {
        //given
        portfolioDto.setUsername(NOT_EXISTING_USER_USERNAME);

        //when
        portfolioService.addPortfolio(portfolioDto);

        //then
        //expect exception
    }

    @Test
    public void shouldDeletePortfolio() {
        //given
        //when
        portfolioService.deletePortfolio(portfolio.getId());

        //then
        verify(portfolioRepository).deleteById(portfolio.getId());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToDeleteNonExistingPortfolio() {
        //given
        //when
        portfolioService.deletePortfolio(NOT_EXISTING_PORTFOLIO_ID);

        //then
        //expect exception
    }

    @Test
    public void shouldGetPortfolio() {
        //given
        //when
        Portfolio actual = portfolioService.getPortfolio(portfolio.getId());
        //then
        assertThat(actual).isEqualTo(portfolio);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingPortfolio() {
        //given
        //when
        portfolioService.getPortfolio(NOT_EXISTING_PORTFOLIO_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllPortfolios() {
        //given
        List<Portfolio> portfolios = Lists.newArrayList(
                new Portfolio(user, "PortfolioA"),
                new Portfolio(user, "PortfolioB"),
                new Portfolio(user, "PortfolioC")
        );
        given(portfolioRepository.findAll()).willReturn(portfolios);

        //when
        List<Portfolio> actual = portfolioService.getAllPortfolios();

        //then
        assertThat(actual).containsExactlyElementsOf(portfolios);
    }

    @Test
    public void shouldGetNoPortfolios() {
        //given
        given(portfolioRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<Portfolio> actual = portfolioService.getAllPortfolios();
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldGetPortfoliosByUser() {
        //given
        List<Portfolio> portfolios = Lists.newArrayList(
                new Portfolio(user, "PortfolioA"),
                new Portfolio(user, "PortfolioB"),
                new Portfolio(user, "PortfolioC")
        );

        given(portfolioRepository.findAllByUser_Username(user.getUsername())).willReturn(portfolios);
        //when
        List<Portfolio> actual = portfolioService.getAllPortfoliosByUser(user.getUsername());
        //then
        assertThat(actual).containsExactlyElementsOf(portfolios);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetPortfolioByNonExistingUser() {
        //given
        //when
        portfolioService.getAllPortfoliosByUser(NOT_EXISTING_USER_USERNAME);
        //then
        //expect exception
    }

    @Test
    public void shouldAddImage(){
        //given
        image.setId(null);

        //when
        Image added = portfolioService.addImage(imageDto);

        //then
        assertThat(added).isEqualTo(image);
        verify(imageRepository).save(image);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddImageIfPortfolioDoesNotExist() {
        //given
        imageDto.setPortfolioId(NOT_EXISTING_PORTFOLIO_ID);

        //when
        portfolioService.addImage(imageDto);

        //then
        //expect exception
    }

    @Test
    public void shouldDeleteImage() {
        //given
        //when
        portfolioService.deleteImage(image.getId());

        //then
        verify(imageRepository).deleteById(image.getId());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToDeleteNonExistingImage() {
        //given
        //when
        portfolioService.deleteImage(NOT_EXISTING_IMAGE_ID);

        //then
        //expect exception
    }

    @Test
    public void shouldGetImage() {
        //given
        //when
        Image actual = portfolioService.getImage(image.getId());
        //then
        assertThat(actual).isEqualTo(image);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingImage() {
        //given
        //when
        portfolioService.getPortfolio(NOT_EXISTING_PORTFOLIO_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllImages() {
        //given
        List<Image> images = Lists.newArrayList(
                new Image(portfolio),
                new Image(portfolio),
                new Image(portfolio)
        );

        given(imageRepository.findAll()).willReturn(images);

        //when
        List<Image> actual = portfolioService.getAllImages();

        //then
        assertThat(actual).containsExactlyElementsOf(images);
    }

    @Test
    public void shouldGetNoImages() {
        //given
        given(imageRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<Image> actual = portfolioService.getAllImages();
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldGetImagesByPortfolio() {
        //given
        List<Image> images = Lists.newArrayList(
                new Image(portfolio),
                new Image(portfolio),
                new Image(portfolio)
        );

        given(imageRepository.findAllByPortfolio_Id(portfolio.getId())).willReturn(images);
        //when
        List<Image> actual = portfolioService.getAllImagesByPortfolio(portfolio.getId());
        //then
        assertThat(actual).containsExactlyElementsOf(images);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGeImageByNonExistingPortfolio() {
        //given
        //when
        portfolioService.getAllImagesByPortfolio(NOT_EXISTING_PORTFOLIO_ID);
        //then
        //expect exception
    }

}
