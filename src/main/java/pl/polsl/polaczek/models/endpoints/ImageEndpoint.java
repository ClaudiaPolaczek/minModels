package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.dto.PortfolioDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.entities.Image;
import pl.polsl.polaczek.models.entities.Portfolio;
import pl.polsl.polaczek.models.services.PortfolioService;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

@RequestMapping(value = "images", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ImageEndpoint {

    private final PortfolioService portfolioService;

    @Autowired
    ImageEndpoint(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{id}")
    public Image get(@PathVariable Long id){
        return portfolioService.getImage(id);
    }

    /*@GetMapping("/{id}")
    public InputStream getInputStream(@PathVariable Long id){
        return portfolioService.getImageStream(id);
    }*/

    @GetMapping
    public List<Image> getAll(){
        return portfolioService.getAllImages();
    }

    @GetMapping("/portfolio/{portfolioId}")
    public List<Image> getAllImagesByPortfolio(@PathVariable Long portfolioId){
        return portfolioService.getAllImagesByPortfolio(portfolioId);
    }

    @PostMapping
    public Image addImage(@Valid @RequestBody ImageDto dto) {
        return portfolioService.addImage(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable Long id){
        portfolioService.deleteImage(id);
    }
}
