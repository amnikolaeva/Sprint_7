import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginApiNegativeTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        courierClient.courierCreate(courier);
    }

    @Test
    public void CourierLoginWithoutLogin() {
        courier.setLogin(null);
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void CourierLoginWithoutPassword() {
        courier.setPassword(null);
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void CourierLoginWithIncorrectLogin() {
        courier.setLogin("test");
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void CourierLoginWithIncorrectPassword() {
        courier.setPassword("test");
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
