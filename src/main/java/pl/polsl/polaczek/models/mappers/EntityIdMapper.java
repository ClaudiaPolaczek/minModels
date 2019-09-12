package pl.polsl.polaczek.models.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.polaczek.models.dao.*;
import pl.polsl.polaczek.models.entities.*;

@Component
public class EntityIdMapper{

    private CommentRepository commentRepository;
    private ImageRepository imageRepository;
    private PhotoShootRepository photoShootRepository;
    private ModelRepository modelRepository;
    private PhotographerRepository photographerRepository;
    private PortfolioRepository portfolioRepository;

    @Autowired
    EntityIdMapper(CommentRepository commentRepository, ImageRepository imageRepository,
                   PhotoShootRepository photoShootRepository, ModelRepository modelRepository,
                   PhotographerRepository photographerRepository, PortfolioRepository portfolioRepository) {

        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
        this.photoShootRepository = photoShootRepository;
        this.modelRepository = modelRepository;
        this.photographerRepository = photographerRepository;
        this.portfolioRepository = portfolioRepository;
    }

    Comment toComment(Long id){
        return commentRepository.findById(id).orElseThrow();
    }

    Image toImage(Long id){
        return imageRepository.findById(id).orElseThrow();
    }

    PhotoShoot toMeeting(Long id){
        return photoShootRepository.findById(id).orElseThrow();
    }

    Model toModel(Long id){
        return modelRepository.findById(id).orElseThrow();
    }

    Photographer toPhotographer(Long id){
        return photographerRepository.findById(id).orElseThrow();
    }

    Portfolio toPortfolio(Long id){
        return portfolioRepository.findById(id).orElseThrow();
    }

}
