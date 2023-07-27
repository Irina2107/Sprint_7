package com.example.courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAssertions {
    @Step("Успешное создание пользователя")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }
    @Step("Успешный вход в систему и получение id курьера")
    public int loggedInSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");

    }
    @Step("Проверка, что система вернёт ошибку, если создаем пользователя с существующим логином")
    public void notCreatedSuccessfullyWithExistentLogin(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }
    @Step("Проверка, что система вернёт ошибку, если создаем пользователя без логина")
    public void notCreatedWithoutLogIn(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Step("Проверка, что система вернёт ошибку, если пользователь входит в систему без логина или пароля")
    public void canNotLogInWithoutCredentials(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Step("Проверка, что система вернёт ошибку, если пользователь входит в систему с несущестующими логином или паролем")
    public void canNotLogInWithNonexistentCredentials(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Step("Проверка удаления курьера")
    public void deleteCourier(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK) // проверяется код состояния ответа
                .body("ok", equalTo(true));
            }


}
