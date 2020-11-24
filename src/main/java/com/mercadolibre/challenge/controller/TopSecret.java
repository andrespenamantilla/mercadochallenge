package com.mercadolibre.challenge.controller;

import com.mercadolibre.challenge.error.SpaceShipConflictError;
import com.mercadolibre.challenge.iservices.ISpaceShipLocationService;
import com.mercadolibre.challenge.pojos.Satellite;
import com.mercadolibre.challenge.pojos.SpaceShipResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TopSecret {

    @Autowired
    ISpaceShipLocationService spaceShipLocationService;

    @PostMapping("/topsecret")
    public ResponseEntity findMessageAndPosition(@RequestBody List<Satellite> satellites) {
        try {

            ResponseEntity responseEntity = null;
            Boolean peititionOk = spaceShipLocationService.validateIfThePetitionIsOk(satellites);
            if (peititionOk) {
                SpaceShipResponse response = spaceShipLocationService.getLocationAndMessage(satellites);
                if (response == null) {
                    responseEntity = new ResponseEntity("Wrong distance or can not unlock the message", HttpStatus.NOT_FOUND);
                }
                if (response != null) {
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
