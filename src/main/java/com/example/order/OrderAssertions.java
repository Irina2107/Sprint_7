package com.example.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssertions {
    @Step("Проверка создания заказа")
    public int createdOrderSuccessfully(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track");

    }
    @Step("Проверка удаления заказа")
    public void cancelOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }
    @Step("Проверка получения списка заказов")
    public void getOrderList(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());

    }
}
