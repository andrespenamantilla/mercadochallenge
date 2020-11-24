package com.mercadolibre.challenge.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpaceShipConflictError extends RuntimeException{

    private HttpStatus status;
    public SpaceShipConflictError(String message, HttpStatus status ) {
        super(message);
        this.status= status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
