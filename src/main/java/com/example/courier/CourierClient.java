package com.example.courier;
import com.example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends Client {
    static final String COURIER_API = "/courier";

    @Step
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API)   // CREATE
                .then().log().all();
    }

    @Step
    public ValidatableResponse logIn(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_API + "/login")  // LOG IN
                .then().log().all();
    }
    @Step
    public void deleteCourier(int id) {
        spec()
                .delete(COURIER_API + String.format("/%d", id))
                .then().log().all();

    }
}