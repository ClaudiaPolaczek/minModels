package pl.polsl.polaczek.models.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.polaczek.models.dto.PortfolioDto;
import pl.polsl.polaczek.models.entities.Portfolio;
import pl.polsl.polaczek.models.services.PortfolioService;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "portfolios", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PortfolioEndpoint {

    private final PortfolioService portfolioService;

    @Autowired
    PortfolioEndpoint(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{id}")
    public Portfolio get(@PathVariable Long id){
        return portfolioService.getPortfolio(id);
    }

    @GetMapping
    public List<Portfolio> getAll(){
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/u/{userUsername}")
    public List<Portfolio> getAllPortfoliosByUser(@PathVariable String userUsername){
        return portfolioService.getAllPortfoliosByUser(userUsername);
    }

    @PostMapping
    public Portfolio addPortfolio(@Valid @RequestBody PortfolioDto dto) {
        return portfolioService.addPortfolio(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePortfolio(@PathVariable Long id){
        portfolioService.deletePortfolio(id);
    }

    @PatchMapping("/edit/{id}")
    public Portfolio editPortfolio(@PathVariable Long id, @Valid @RequestBody PortfolioDto dto){
        return portfolioService.editPortfolio(id, dto);
    }

    @PatchMapping("/photo/{id}")
    public Portfolio addMainPhotoUrl(@PathVariable Long id, @Valid @RequestBody PortfolioDto dto){
        return portfolioService.addMainPhotoUrl(id, dto.getMainPhotoUrl());
    }
}
