package order;

import client.OrderClient;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersApiTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderCanBeCreatedWithBlackColor() {
        File json = new File("src/test/resources/order.json");
        orderClient.orderCreate(json)
                .statusCode(201)
                .body("track", notNullValue());
    }
}
