package pl.polsl.polaczek.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private String entity;
    private String field;
    private String value;
    private String reason;

    public BadRequestException(String entity, String id, String value, String reason) {
        super(entity + " with " + id + "=" + value + " " + reason);
        this.entity = entity;
        this.field = id;
        this.value = value;
        this.reason = reason;
    }

    public String getEntity() { return entity; }

    public String getField() { return field; }

    public String getValue() {return value; }

    public String getReason() { return reason; }
}
