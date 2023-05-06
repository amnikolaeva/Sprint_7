package order;

import client.OrderClient;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListApiTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderListIsNotEmpty() {
        List<Object> orderList = orderClient.orderList()
                .statusCode(200)
                .body("orders", notNullValue())
                .extract().path("orders");

        Assert.assertFalse(orderList.isEmpty());
    }
}
