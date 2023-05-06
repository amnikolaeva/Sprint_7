package order;

import client.OrderClient;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersApiTest {

    private static final String BASE_PATH = "src/test/resources/";
    private static final String ORDER_BLACK_PATH = BASE_PATH + "orderBlack.json";
    private static final String ORDER_GREY_PATH = BASE_PATH + "orderGrey.json";
    private static final String ORDER_BLACK_AND_GREY_PATH = BASE_PATH + "orderBlackAndGrey.json";
    private static final String ORDER_WITHOUT_COLOR_PATH = BASE_PATH + "orderWithoutColor.json";

    private OrderClient orderClient;
    private final File json;

    public OrdersApiTest(File json) {
        this.json = json;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new File(ORDER_BLACK_PATH)},
                {new File(ORDER_GREY_PATH)},
                {new File(ORDER_BLACK_AND_GREY_PATH)},
                {new File(ORDER_WITHOUT_COLOR_PATH)}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void orderCanBeCreated() {
        orderClient.orderCreate(json)
                .statusCode(201)
                .body("track", notNullValue());
    }
}
