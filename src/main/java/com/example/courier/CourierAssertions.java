package com.example.courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAssertions {
    @Step
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }
    @Step
    public int loggedInSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");

    }
    @Step
    public void notCreatedSuccessfullyWithExistentLogin(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }
    @Step
    public void notCreatedWithoutLogIn(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Step
    public void canNotLogInWithoutCredentials(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Step
    public void canNotLogInWithNonexistentCredentials(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

}
