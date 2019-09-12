package pl.polsl.polaczek.models.services;

import org.springframework.stereotype.Service;
import pl.polsl.polaczek.models.dao.ImageRepository;
import pl.polsl.polaczek.models.dao.PortfolioRepository;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ImageRepository imageRepository;

    public PortfolioService(final PortfolioRepository portfolioRepository, final ImageRepository imageRepository){
        this.portfolioRepository = portfolioRepository;
        this.imageRepository = imageRepository;
    }
}
