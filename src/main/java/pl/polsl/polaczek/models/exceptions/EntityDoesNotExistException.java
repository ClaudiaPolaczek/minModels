package pl.polsl.polaczek.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityDoesNotExistException extends RuntimeException {
    private String entity;
    private String id;
    private String value;

    public EntityDoesNotExistException(String entity, String id, String value) {
        super(entity + " with " + id + "=" + value + " does not exist");
        this.entity = entity;
        this.id = id;
        this.value = value;
    }

    public String getEntity() {
        return entity;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
