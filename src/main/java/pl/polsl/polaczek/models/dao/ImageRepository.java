package pl.polsl.polaczek.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.polaczek.models.entities.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPortfolio_Id(Long portfolioId);
    List<Image> findAllByFileUrl(String fileUrl);
}
