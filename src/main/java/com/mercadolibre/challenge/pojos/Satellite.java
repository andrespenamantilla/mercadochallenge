package com.mercadolibre.challenge.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Satellite {

    private String name;
    private Double distance;
    private String message[];
}
