package com.mercadolibre.challenge.pojos;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;

import lombok.Data;

@Data
public class Satellite {

    private String name;
    private Double distance;
    private String message[];
}
