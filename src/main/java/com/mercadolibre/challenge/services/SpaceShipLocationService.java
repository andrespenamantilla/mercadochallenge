package com.mercadolibre.challenge.services;

import com.mercadolibre.challenge.utils.Constants;
import com.mercadolibre.challenge.utils.Utils;
import com.mercadolibre.challenge.iservices.ISpaceShipLocationService;
import com.mercadolibre.challenge.pojos.Position;
import com.mercadolibre.challenge.pojos.Satellite;
import com.mercadolibre.challenge.pojos.SpaceShipResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SpaceShipLocationService implements ISpaceShipLocationService {

    /*
     * I Know that the distance between 2 points is the radio for each satellite
     *d(A,B) = √(x2-x1)² -(y2-y1)²
     * */
    public Double getDistance(Position position) {
        return Math.sqrt(Utils.squaredNumber(position.getX() - Constants.SPACE_SHIP.getX()) +
                Utils.squaredNumber(position.getY() - Constants.SPACE_SHIP.getY()));
    }

    /**
     * x1 = -500,  y1 = -200, r1 = kenobiDistance
     * x2 = 100,  y2 = -100, r2 = skywalkerDistance
     * x3 = 500,  y3 = 100, r3 = satoDistance
     */


    /***
     * This function obtain the close value between three satellites
     * */
    @Override
    public Position getLocation(Double kenobiDistance, Double skywalkerDistance, Double satoDistance) {

        Double A, B, C, D, E, F, X, Y;

        A = (2 * Constants.ANAKYN_SKYWALKER.getX()) - (2 * Constants.OBI_WAN_KENOBI.getX());
        B = (2 * Constants.ANAKYN_SKYWALKER.getY()) - (2 * Constants.OBI_WAN_KENOBI.getY());
        C = Utils.squaredNumber(kenobiDistance) - Utils.squaredNumber(skywalkerDistance) - Utils.squaredNumber(Constants.OBI_WAN_KENOBI.getX())
                + Utils.squaredNumber(Constants.ANAKYN_SKYWALKER.getX()) - Utils.squaredNumber(Constants.OBI_WAN_KENOBI.getY())
                + Utils.squaredNumber(Constants.ANAKYN_SKYWALKER.getY());
        D = (2 * Constants.JUN_SATO.getX()) - (2 * Constants.ANAKYN_SKYWALKER.getX());
        E = (2 * Constants.JUN_SATO.getY()) - (2 * Constants.ANAKYN_SKYWALKER.getY());
        F = Utils.squaredNumber(skywalkerDistance) - Utils.squaredNumber(satoDistance) - Utils.squaredNumber(Constants.ANAKYN_SKYWALKER.getX())
                + Utils.squaredNumber(Constants.JUN_SATO.getX()) - Utils.squaredNumber(Constants.ANAKYN_SKYWALKER.getY())
                + Utils.squaredNumber(Constants.JUN_SATO.getY());
        X = (C * E - F * B) / (E * A - B * D);
        Y = (C * D - A * F) / (B * D - A * E);
        return new Position(X, Y);

    }


    /***
     * This function decrypt de message
     * */
    @Override
    public String getMessage(List<String[]> codes) {
        String[] code = new String[5];
        String messageDecrypted = "";

        codes.forEach(message -> {
            for (int i = 0; i < message.length; i++) {
                if (!message[i].equals("")) {
                    code[i] = message[i];
                }
                if (message[i] == null) {
                    code[i] = "";
                }
            }
        });

        for (int i = 0; i < code.length; i++) {
            messageDecrypted = messageDecrypted + code[i] + " ";

        }
        messageDecrypted = messageDecrypted.substring(0, messageDecrypted.length() - 1);
        return messageDecrypted;
    }


    /***
     * This function validate if the message is not corrupt or desfased
     * */
    @Override
    public Boolean validateIfADecryptedMessageIsDesfasedOrCorrupt(String messageDecrypted) {
        Set<String> messageDecryptedSet = new LinkedHashSet<>();
        Arrays.stream(messageDecrypted.trim().split(" ")).forEach(word -> {
            messageDecryptedSet.add(word);
        });

        if (messageDecryptedSet.size() < 5)
            return true;
        else
            return false;
    }


    /***
     * Given the information of three satellites thiw function calculate
     * the position and the message an encapsulates it on a respose
     * */
    @Override
    public SpaceShipResponse getLocationAndMessage(List<Satellite> satellites) {
        List<String[]> codes = new ArrayList<>();
        AtomicReference<Double> kenobiDistance = new AtomicReference<>(0d);
        AtomicReference<Double> skywalkerDistance = new AtomicReference<>(0d);
        AtomicReference<Double> satoDistance = new AtomicReference<>(0d);
        satellites.forEach(satellite -> {
            if (satellite.getName().equalsIgnoreCase("kenobi")) {
                kenobiDistance.set(satellite.getDistance());
            }
            if (satellite.getName().equalsIgnoreCase("skywalker")) {
                skywalkerDistance.set(satellite.getDistance());

            }
            if (satellite.getName().equalsIgnoreCase("sato")) {
                satoDistance.set(satellite.getDistance());
            }
            codes.add(satellite.getMessage());
        });

        Position position = getLocation(kenobiDistance.get(), skywalkerDistance.get(), satoDistance.get());
        String message = getMessage(codes);

        Double kenobiRealDistance = getDistance(Constants.OBI_WAN_KENOBI);
        Double skywalkerRealDistance = getDistance(Constants.ANAKYN_SKYWALKER);
        Double satoRealDistance = getDistance(Constants.JUN_SATO);


        if (!validateIfADecryptedMessageIsDesfasedOrCorrupt(message) &&
                validateIfTheDistancesAreClose(kenobiRealDistance, kenobiDistance.get()) &&
                validateIfTheDistancesAreClose(skywalkerRealDistance, skywalkerDistance.get()) &&
                validateIfTheDistancesAreClose(satoRealDistance, satoDistance.get())) {
            return new SpaceShipResponse(position, message);
        } else
            return null;
    }


    /***
     * Given the information of three satellites thiw function calculate
     * the position and the message an encapsulates it on a respose
     * */
    @Override
    public Boolean validateIfThePetitionIsOk(List<Satellite> satellites) {
        AtomicReference<Boolean> petitionOk = new AtomicReference<>(false);
            if (satellites.size() > 0) {
                satellites.forEach(satellite -> {
                    if (!satellite.getName().equals("") || satellite.getName() != null) {
                        petitionOk.set(true);
                    }
                    if (satellite.getDistance() != null && satellite.getDistance() > 0) {
                        petitionOk.set(true);
                    }
                    if (satellite.getMessage() != null && satellite.getMessage().length > 0) {
                        petitionOk.set(true);
                    }
                });
            }
        return petitionOk.get();
    }

    /***
     * this function validate if the given distance and the knowing distance are so close
     * if the distances are close returns returns true and it indicates that the math operation is right
     * */
    public Boolean validateIfTheDistancesAreClose(Double realDistance, Double givenDistance) {
        if ((realDistance - givenDistance <= 1d && realDistance - givenDistance >= 0d)
                || (givenDistance - realDistance <= 1d && givenDistance - realDistance >= 0d)) {
            return true;
        } else
            return false;
    }

    /***
     * this functions validates the scenario when the petition is submited in pieces ( POST split service )
     * */
    @Override
    public List<Satellite> validateSplitPetition(Satellite satellite, List<Satellite> satellites) {
        if (satellites.size() < 3) {
            satellites.add(satellite);
        }
        return satellites;
    }
}
