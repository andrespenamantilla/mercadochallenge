package com.mercadolibre.challenge.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpaceShipResponse {

    private Position position;
    private String message;
}
