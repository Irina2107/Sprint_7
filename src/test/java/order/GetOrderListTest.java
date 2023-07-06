package order;

import com.example.order.OrderAssertions;
import com.example.order.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions orderAssertions = new OrderAssertions();

    @Test
    @DisplayName("Получить список заказаов")
    public void checkGetOrders() {
        ValidatableResponse orderResponse = orderClient.getOrderList();
        orderAssertions.getOrderList(orderResponse);
        orderResponse.body(notNullValue());
    }
}

