package courier;
import com.example.courier.Courier;
import com.example.courier.CourierAssertions;
import com.example.courier.CourierClient;
import com.example.courier.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class CreateCourierNegTest {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров и проверка текста сообщения")
    public void canNotCreateTwoSameCourier(){
        Courier courier = new Courier("Test33", "P@ssword", "Testa");
        client.createCourier(courier);
        ValidatableResponse courierResponse = client.createCourier(courier);
        check.notCreatedSuccessfullyWithExistentLogin(courierResponse);
    }
    @Test
    @DisplayName("Создание с существующим логином и проверка текста сообщения")
    public void canNotCreateCourierWithSameLogin(){
        Courier courier = new Courier("Test33", "P@ssword!", "Test");
        client.createCourier(courier);
        ValidatableResponse courierResponse = client.createCourier(courier);
        check.notCreatedSuccessfullyWithExistentLogin(courierResponse);
    }
    @Test
    @DisplayName("Создание курьера без логина и проверка текста сообщения")
    public void canNotCreateCourierWithoutLogIn(){
        var courier = generator.random();
        courier.setLogin(null);
        ValidatableResponse courierResponse = client.createCourier(courier);
        check.notCreatedWithoutLogIn(courierResponse);
    }
}
