package courier;
import com.example.courier.CourierAssertions;
import com.example.courier.CourierClient;
import com.example.courier.CourierGenerator;
import com.example.courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class CourierLogInPosTest {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();
    protected int courierId;

    @After
    public void deleteCourier() {
        client.deleteCourier(courierId);
    }
    @Test
    @DisplayName("курьер может авторизоваться, для авторизации нужно передать все обязательные поля, успешный запрос возвращает id")
    public void courierLogInSuccesfully(){
        var courier = generator.random();
        client.createCourier(courier);
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId > 100;
    }
}
