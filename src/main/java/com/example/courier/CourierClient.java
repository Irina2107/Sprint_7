package com.example.courier;
import com.example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends Client {
    private static final String COURIER_API = "/courier";


    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API)   // CREATE
                .then().log().all();// логи
    }
    @Step("Успешный вход в систему")
    public ValidatableResponse logIn(Credentials credentials) {
        return spec()
                .body(credentials)
                .when()
                .post(COURIER_API + "/login")  // LOG IN
                .then().log().all();

    }
    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
       return spec()

                //   .delete(COURIER_API + String.format("/%d", id))
                //.body("id")
                .when()
                //.delete(COURIER_API + String.format("/%d", id))
                .delete(COURIER_API + "/" + id)
                .then().log().all();
    }
}