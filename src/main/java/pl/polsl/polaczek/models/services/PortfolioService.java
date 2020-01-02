package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ImageRepository;
import pl.polsl.polaczek.models.dao.PortfolioRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.dto.PortfolioDto;
import pl.polsl.polaczek.models.entities.Image;
import pl.polsl.polaczek.models.entities.Portfolio;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private AmazonClient amazonClient;

    @Autowired
    public PortfolioService(final PortfolioRepository portfolioRepository, final ImageRepository imageRepository,
                            final UserRepository userRepository, AmazonClient amazonClient){
        this.portfolioRepository = portfolioRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.amazonClient = amazonClient;
    }

    public Portfolio getPortfolio(Long id){
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Portfolio", "id", id.toString()));
    }

    public List<Portfolio> getAllPortfolios(){
        return portfolioRepository.findAll();
    }

    public List<Portfolio> getAllPortfoliosByUser(String username){
        userRepository.findById(username).orElseThrow(()
                -> new EntityDoesNotExistException("User","username",username));

        return portfolioRepository.findAllByUser_Username(username);
    }

    public Image getImage(Long id){
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Image", "id", id.toString()));
    }

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    public List<Image> getAllImagesByPortfolio(Long portfolioId){

        portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new EntityDoesNotExistException("Portfolio", "id", portfolioId.toString()));

        return imageRepository.findAllByPortfolio_Id(portfolioId);
    }

    public Image addImage(@NonNull ImageDto imageDto){
        return convertImageDtoToEntity(imageDto);
    }

    private Image convertImageDtoToEntity(ImageDto dto) {
        Image image = new Image(portfolioRepository.findById(dto.getPortfolioId()).orElseThrow
                (() -> new BadRequestException("Portfolio", "id", dto.getPortfolioId().toString(), "does not exist")),
                dto.getFileUrl(), dto.getName());

        imageRepository.save(image);
        return image;
    }

    public Portfolio addPortfolio(@NonNull PortfolioDto portfolioDto){
        Portfolio portfolio = convertPortfolioDtoToEntity(portfolioDto);
        return portfolioRepository.save(portfolio);
    }

    private Portfolio convertPortfolioDtoToEntity(PortfolioDto dto) {

        Portfolio portfolio = new Portfolio(userRepository.findById(dto.getUsername()).orElseThrow
                (() -> new BadRequestException("User", "id", dto.getUsername(), "does not exist")), dto.getName());

        portfolio.setDescription(dto.getDescription());

        return portfolio;
    }

    public void deletePortfolio(@NonNull Long id){
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Portfolio","id",id.toString()));

        List<Image> images = imageRepository.findAllByPortfolio_Id(id);

        for(Image image: images){
            deleteImage(image.getId());
        }
        portfolioRepository.deleteById(id);
    }

    public String deleteImage(@NonNull Long id){
        Image image = imageRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Image","id",id.toString()));

        String message = this.amazonClient.deleteFileFromS3Bucket(image.getFileUrl());

        String main = image.getPortfolio().getMainPhotoUrl();
        if(main!=null) {
            if (main.equals(image.getFileUrl())) {
                image.getPortfolio().setMainPhotoUrl(null);
            }
        }

        String mainProfile = image.getPortfolio().getMainPhotoUrl();
        if(mainProfile!=null) {
            if (mainProfile.equals(image.getFileUrl())) {
                image.getPortfolio().getUser().setMainPhotoUrl(null);
            }
        }

        imageRepository.save(image);
        imageRepository.deleteById(id);
        return message;
    }

    public void deleteImageByUrl(@NonNull String url){

        List<Image> images = imageRepository.findAllByName(url);

        for(Image image: images){
            this.amazonClient.deleteFileFromS3Bucket(image.getFileUrl());
            imageRepository.delete(image);
        }
    }

    public Portfolio editPortfolio(@NonNull Long id, @NonNull PortfolioDto portfolioDto){

            Portfolio portfolio = portfolioRepository.findById(id)
                    .orElseThrow(() -> new EntityDoesNotExistException("Portfolio", "id", id.toString()));

            portfolio.setName(portfolioDto.getName());
            portfolio.setDescription(portfolioDto.getDescription());

            return portfolioRepository.save(portfolio);
    }

    public Portfolio addMainPhotoUrl(@NonNull Long id, String url){

        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Portfolio", "id", id.toString()));

        portfolio.setMainPhotoUrl(url);

        return portfolioRepository.save(portfolio);
    }
}
