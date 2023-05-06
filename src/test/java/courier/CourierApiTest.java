package courier;

import client.CourierClient;
import generator.CourierGenerator;
import io.restassured.RestAssured;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierApiTest {

    private CourierClient courierClient;
    private Integer courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void courierCanBeCreates() {
        courierClient.courierCreate(courier)
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    public void courierCanBeCreatesWithLoginAndPassword() {
        courier.setFirstName(null);
        courierClient.courierCreate(courier)
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }
}
