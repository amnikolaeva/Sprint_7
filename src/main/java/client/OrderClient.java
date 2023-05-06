package client;

import io.restassured.response.ValidatableResponse;

import java.io.File;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String CREATE_ORDER_URL = "/api/v1/orders";
    private static final String ORDER_LIST_URL = "/api/v1/orders";

    public ValidatableResponse orderCreate(File json) {
        return given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(CREATE_ORDER_URL)
                .then();
    }

    public ValidatableResponse orderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_LIST_URL)
                .then();
    }
}
