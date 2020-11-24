package com.mercadolibre.challenge.iservices;

import com.mercadolibre.challenge.pojos.Position;
import com.mercadolibre.challenge.pojos.Satellite;
import com.mercadolibre.challenge.pojos.SpaceShipResponse;

import java.util.List;

public interface ISpaceShipLocationService {

    Double getDistance(Position position);

    Position getLocation(Double kenobiDistance, Double skywalkerDistance, Double satoDistance);

    String getMessage(List<String[]> codes);

    Boolean validateIfADecryptedMessageIsDesfasedOrCorrupt(String messageDecrypted);

    SpaceShipResponse getLocationAndMessage(List<Satellite> satellites);
}
