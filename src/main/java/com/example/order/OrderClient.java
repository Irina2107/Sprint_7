package com.example.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import com.example.Client;

public class OrderClient extends Client {
    static final String ORDER_API = "/orders";

    @Step
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_API)   // CREATE
                .then().log().all();
    }

    @Step
    public ValidatableResponse getOrderList(){
        return spec()
                .get(ORDER_API) //GET
                .then().log().all();
    }

    @Step
    public ValidatableResponse cancelOrder(int id){
        return spec()
                .queryParam("track", id)
                .put(ORDER_API+ "/cancel")   // CANCEL
                .then().log().all();
    }
}
