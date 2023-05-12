package courier;

import client.CourierClient;
import generator.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.Courier;
import model.CourierCredentials;
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
    @DisplayName("Авторизация курьера без логина")
    public void CourierLoginWithoutLogin() {
        courier.setLogin(null);
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    public void CourierLoginWithoutPassword() {
        courier.setPassword(null);
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным логином")
    public void CourierLoginWithIncorrectLogin() {
        courier.setLogin("test");
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным паролем")
    public void CourierLoginWithIncorrectPassword() {
        courier.setPassword("test");
        courierClient.login(CourierCredentials.from(courier))
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
