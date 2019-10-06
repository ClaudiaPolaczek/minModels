package pl.polsl.polaczek.models.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ImageRepository;
import pl.polsl.polaczek.models.dao.ImageStore;
import pl.polsl.polaczek.models.dao.PortfolioRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.dto.PortfolioDto;
import pl.polsl.polaczek.models.entities.Image;
import pl.polsl.polaczek.models.entities.Portfolio;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageStore imageStore;

    @Autowired
    public PortfolioService(final PortfolioRepository portfolioRepository, final ImageRepository imageRepository,
                            final UserRepository userRepository, final ImageStore imageStore){
        this.portfolioRepository = portfolioRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.imageStore = imageStore;
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

    public InputStream getImageStream(Long id){

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Image", "id", id.toString()));

        return imageStore.getContent(image);
    }

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    public List<Image> getAllImagesByPortfolio(Long portfolioId){

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new EntityDoesNotExistException("Portfolio", "id", portfolioId.toString()));

        return imageRepository.findAllByPortfolio_Id(portfolioId);
    }

    public Image addImage(@NonNull ImageDto imageDto){

        Image image = convertImageDtoToEntity(imageDto);
        return image;
    }

    private Image convertImageDtoToEntity(ImageDto dto) {

        Image image = new Image(portfolioRepository.findById(dto.getPortfolioId()).orElseThrow
                (() -> new BadRequestException("Portfolio", "id", dto.getPortfolioId().toString(), "does not exist")));

        image.setTitle(dto.getTitle());
        imageRepository.save(image);

        try {
            imageStore.setContent(image, new FileInputStream(dto.getFilePath()));
        } catch (FileNotFoundException e) {
            throw new BadRequestException("File", "path", dto.getFilePath(), "can't be found");
        }

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
        portfolioRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Portfolio","id",id.toString()));

        portfolioRepository.deleteById(id);
    }

    public void deleteImage(@NonNull Long id){
        Image image = imageRepository.findById(id).orElseThrow(()
                -> new EntityDoesNotExistException("Image","id",id.toString()));

        imageStore.unsetContent(image);

        imageRepository.deleteById(id);
    }
}
