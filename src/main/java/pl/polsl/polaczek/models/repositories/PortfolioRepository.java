package pl.polsl.polaczek.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.polaczek.models.entities.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
