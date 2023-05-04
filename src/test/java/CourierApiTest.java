import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

public class CourierApiTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void courierCanBeCreates() {
        Courier courier = new Courier("login", "password", "firstName");
        CourierClient courierClient = new CourierClient();
        ValidatableResponse createResponse = courierClient.courierCreate(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int courierId = loginResponse.extract().path("id");
    }
}
