package com.mercadolibre.challenge;

import com.mercadolibre.challenge.iservices.ISpaceShipLocationService;
import com.mercadolibre.challenge.pojos.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChallengeApplicationTests {

    @Autowired
    private ISpaceShipLocationService spaceShipLocationService;

    List<String[]> satellitesWithValidMessage = new ArrayList<>();;
    List<String[]> satellitesWithNotAValidMessage = new ArrayList<>();;
    String[] skywalkerMessages = new String[5], kenobyMessages = new String[5], satoMessages = new String[5];

    @Test
    void contextLoads() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void assertThatKenobiDistanceToShipIsBetween485And486Units() {
        Double kenobiDistance = spaceShipLocationService.getDistance(Constants.OBI_WAN_KENOBI);
        assert (kenobiDistance > 485d && kenobiDistance < 486d);
    }

    @Test
    public void assertThatSkywalkerDistanceToShipIsBetween265And266Units() {
        Double skywalkerDistance = spaceShipLocationService.getDistance(Constants.ANAKYN_SKYWALKER);
        assert (skywalkerDistance > 265d && skywalkerDistance < 266d);
    }

    @Test
    public void assertThatSatoDistanceToShipIsBetween265And266Units() {
        Double satoDistance = spaceShipLocationService.getDistance(Constants.JUN_SATO);
        assert (satoDistance > 600d && satoDistance < 601d);
    }

    @Test
    public void assertThatSpaceShipXAxxisIsCloseToMinus100AndYAxxisIsCloseTo75dot5() {

        Double kenobiDistance = spaceShipLocationService.getDistance(Constants.OBI_WAN_KENOBI);
        Double skywalkerDistance = spaceShipLocationService.getDistance(Constants.ANAKYN_SKYWALKER);
        Double satoDistance = spaceShipLocationService.getDistance(Constants.JUN_SATO);
        Position spaceShip = spaceShipLocationService.getLocation(kenobiDistance, skywalkerDistance, satoDistance);
        assert (spaceShip.getX() > -101d && spaceShip.getX() < -99d);
        assert (spaceShip.getY() >= 75d && spaceShip.getY() < 76d);
    }

    @Test
    public void validateIfSecretMessageIsAValidMessage() {
        initializeSatellitesWithValidMessage();
        assert (spaceShipLocationService.getMessage(satellitesWithValidMessage).equals("este es un mensaje secreto"));
        assert (!spaceShipLocationService.validateIfADecryptedMessageIsDesfasedOrCorrupt(spaceShipLocationService.getMessage(satellitesWithValidMessage)));
    }

    @Test
    public void validateIfSecretMessageIsNotAValidMessage() {
        initializeSatellitesWithNotAValidMessage();
        assert(!spaceShipLocationService.getMessage(satellitesWithNotAValidMessage).equals("este es un mensaje secreto"));
        assert (spaceShipLocationService.validateIfADecryptedMessageIsDesfasedOrCorrupt(spaceShipLocationService.getMessage(satellitesWithNotAValidMessage)));
    }

    public void initializeSatellitesWithValidMessage(){
        kenobyMessages[0] = "este";
        kenobyMessages[1] = "";
        kenobyMessages[2] = "";
        kenobyMessages[3] = "mensaje";
        kenobyMessages[4] = "";

        skywalkerMessages[0] = "";
        skywalkerMessages[1] = "es";
        skywalkerMessages[2] = "";
        skywalkerMessages[3] = "";
        skywalkerMessages[4] = "secreto";

        satoMessages[0] = "este";
        satoMessages[1] = "";
        satoMessages[2] = "un";
        satoMessages[3] = "";
        satoMessages[4] = "";

        satellitesWithValidMessage.add(kenobyMessages);
        satellitesWithValidMessage.add(satoMessages);
        satellitesWithValidMessage.add(skywalkerMessages);

    }

    public void initializeSatellitesWithNotAValidMessage(){
        kenobyMessages[0] = "";
        kenobyMessages[1] = "este";
        kenobyMessages[2] = "es";
        kenobyMessages[3] = "un";
        kenobyMessages[4] = "mensaje";

        skywalkerMessages[0] = "";
        skywalkerMessages[1] = "es";
        skywalkerMessages[2] = "";
        skywalkerMessages[3] = "";
        skywalkerMessages[4] = "secreto";

        satoMessages[0] = "este";
        satoMessages[1] = "";
        satoMessages[2] = "un";
        satoMessages[3] = "";
        satoMessages[4] = "";

        satellitesWithNotAValidMessage.add(kenobyMessages);
        satellitesWithNotAValidMessage.add(satoMessages);
        satellitesWithNotAValidMessage.add(skywalkerMessages);
    }
}
