package client;

import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.*;

public class CourierClient extends RestClient {

    private static final String CREATE_COURIER_URL = "/api/v1/courier";
    private static final String LOGIN_COURIER_URL = "api/v1/courier/login";
    private static final String DELETE_COURIER_URL = "/api/v1/courier/:";

    public ValidatableResponse courierCreate(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER_URL)
                .then();
    }

    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(LOGIN_COURIER_URL)
                .then();
    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseSpec())
                .body(id)
                .when()
                .delete(DELETE_COURIER_URL + id)
                .then();
    }
}
