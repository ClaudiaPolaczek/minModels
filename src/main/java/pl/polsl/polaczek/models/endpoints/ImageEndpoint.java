package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.polaczek.models.dto.ImageDto;
import pl.polsl.polaczek.models.entities.Image;
import pl.polsl.polaczek.models.services.AmazonClient;
import pl.polsl.polaczek.models.services.PortfolioService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "images", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ImageEndpoint {

    private final PortfolioService portfolioService;
    private AmazonClient amazonClient;

    @Autowired
    ImageEndpoint(PortfolioService portfolioService, AmazonClient amazonClient) {
        this.portfolioService = portfolioService;
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @PostMapping
    public Image addImage(@Valid @RequestBody ImageDto dto) {
        return portfolioService.addImage(dto);
    }

    @DeleteMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable Long id) {
        return portfolioService.deleteImage(id);
    }

    @GetMapping("/{id}")
    public Image get(@PathVariable Long id){
        return portfolioService.getImage(id);
    }

    @GetMapping
    public List<Image> getAll(){
        return portfolioService.getAllImages();
    }

    @GetMapping("/portfolio/{portfolioId}")
    public List<Image> getAllImagesByPortfolio(@PathVariable Long portfolioId){
        return portfolioService.getAllImagesByPortfolio(portfolioId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable Long id){
        portfolioService.deleteImage(id);
    }

    @DeleteMapping("/delete/url/{url}")
    public void deleteImageByUrl(@PathVariable String url){
        portfolioService.deleteImageByUrl(url);
    }
}
