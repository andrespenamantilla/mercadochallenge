package com.mercadolibre.challenge.controller;

import com.mercadolibre.challenge.error.SpaceShipConflictError;
import com.mercadolibre.challenge.iservices.ISpaceShipLocationService;
import com.mercadolibre.challenge.pojos.Satellite;
import com.mercadolibre.challenge.pojos.SpaceShipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class TopSecretSplit {

    @Autowired
    private ISpaceShipLocationService spaceShipLocationService;

    private List<Satellite> satellites = new ArrayList<>();

    @PostMapping("/topsecret_split")
    public ResponseEntity topSecretSplit(@RequestBody Satellite satellite) {
        try {
            ResponseEntity responseEntity = null;

            if (satellites.size() < 3) {
                satellites = spaceShipLocationService.validateSplitPetition(satellite, satellites);
                responseEntity = new ResponseEntity("Satellite was added", HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity("Error", HttpStatus.NOT_FOUND);
            }
            return responseEntity;

        } catch (SpaceShipConflictError e) {
            throw e;
        }
    }

    @GetMapping("/topsecret_split")
    public ResponseEntity topSecretSplit() {
        try {
            ResponseEntity responseEntity = null;
            Boolean peititionOk = spaceShipLocationService.validateIfThePetitionIsOk(satellites);
            if (peititionOk) {
                SpaceShipResponse response = spaceShipLocationService.getLocationAndMessage(satellites);
                if (response == null) {
                    responseEntity = new ResponseEntity("Wrong distance or can not unlock the message", HttpStatus.NOT_FOUND);
                }
                if (response != null) {
                    satellites = new ArrayList<>();
                    responseEntity = new ResponseEntity(response, HttpStatus.OK);
                }
            } else {
                responseEntity = new ResponseEntity("Json is not valid", HttpStatus.NOT_FOUND);
            }
            return responseEntity;


        } catch (SpaceShipConflictError e) {
            throw e;
        }
    }
}
