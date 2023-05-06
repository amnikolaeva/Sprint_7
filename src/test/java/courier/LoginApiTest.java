package courier;

import client.CourierClient;
import generator.CourierGenerator;
import io.restassured.RestAssured;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginApiTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        courierClient.courierCreate(courier);
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void CourierCanBeLogin() {
        courierId = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }
}
