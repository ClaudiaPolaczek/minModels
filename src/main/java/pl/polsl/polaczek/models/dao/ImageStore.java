package pl.polsl.polaczek.models.dao;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;
import pl.polsl.polaczek.models.entities.Image;

@StoreRestResource(path = "imagescontent")
public interface ImageStore extends ContentStore<Image, String> {
}
