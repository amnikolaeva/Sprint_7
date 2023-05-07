package courier;

import client.CourierClient;
import generator.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierApiNegativeTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

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
    @DisplayName("Создание курьеров с одинаковым логином")
    public void createTheSameCouriers() {
        courierClient.courierCreate(courier)
                .statusCode(201)
                .body("ok", equalTo(true));

        courierClient.courierCreate(courier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createCourierWithoutLogin() {
        courier.setLogin(null);
        courierClient.courierCreate(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createCourierWithoutPassword() {
        courier.setPassword(null);
        courierClient.courierCreate(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
