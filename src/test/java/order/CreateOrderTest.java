package order;
import com.example.order.Order;
import com.example.order.OrderAssertions;
import com.example.order.OrderClient;
import com.example.order.OrderTrack;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions orderAssertions = new OrderAssertions();
    private final OrderTrack orderTrack = new OrderTrack();

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;


    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        Faker faker = new Faker();
        return new Object[][]{

                {faker.name().firstName(), faker.name().lastName(), faker.address().city(), "Сокольники", "+7 921 999 99 99", 1, "10.07.2023", "ЖДУ", List.of("BLACK")},
                {faker.name().firstName(), faker.name().lastName(), faker.address().city(), "Автозаводская", "+7 965 555 55 55", 2, "09.07.2023", "Очень жду", List.of("GREY")},
                {"Тестова", "Теста", "Санкт-Петербург, Невский 8", "Фрунзенская", "+7 986 333 11 00", 3, "09.07.2023", "Перед доставкой позвоните", List.of("BLACK", "GREY")},
                {"Тестер", "Тест", "Москва, Ленинский 142", "Фрунзенская", "+7 921 999 99 99", 0, "10.07.2023", "ЖДУ", List.of()},
        };
    }

    @Test
    @DisplayName("Ордер можно создать")
    public void checkCreateOrder() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse orderResponse = orderClient.createOrder(order);
        orderAssertions.createdOrderSuccessfully(orderResponse);
    }

    @Test
    @DisplayName("Возвращается номер трека")
    public void checkTrackNotNull() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse orderResponse = orderClient.createOrder(order);
        orderResponse.body("track", notNullValue());
    }

    @After
    public void cancelOrder() {
        try {
            orderClient.cancelOrder(orderTrack.getTrack());
        } catch (AssertionError error) {
            System.out.println(error.getMessage());
        }
    }

}

