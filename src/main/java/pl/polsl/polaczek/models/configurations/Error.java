package pl.polsl.polaczek.models.configurations;

import java.io.Serializable;

public class Error implements Serializable {
    private String message;
    public Error() {
    }
    public Error(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message ;
    }
}