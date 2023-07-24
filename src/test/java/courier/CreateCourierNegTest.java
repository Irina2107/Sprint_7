package courier;
import com.example.courier.Courier;
import com.example.courier.CourierClient;
import com.example.courier.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierNegTest {
    private final CourierGenerator generator = new CourierGenerator();
    private CourierClient client;
    private Courier courier;

    @Before
    public void setUp() {
        client = new CourierClient();
        courier = generator.random();
    }
    @Test
    @DisplayName("Создание с существующим логином")
    public void canNotCreateTwoSameCourier(){
        client.createCourier(courier);
        ValidatableResponse courierResponse = client.createCourier(courier);
        int statusCodeSameCourier = courierResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_CONFLICT, statusCodeSameCourier);
        String actualMessage = courierResponse.extract().path("message"); //получене актуального текста сообщения
        Assert.assertEquals("Этот логин уже используется", actualMessage); //ошибка в тексте сообщения
}
    @Test
    @DisplayName("Вместо логина пустота")
    public void canNotCreateCourierNoLogin(){
        Courier courier = new Courier("", "sdfsdfsdfsf121421423", "Test");
        ValidatableResponse courierResponse = client.createCourier(courier);
        int statusCodeNoLogin = courierResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_BAD_REQUEST, statusCodeNoLogin); //ожидаемый код ответа 400
    }
    @Test
    @DisplayName("Создание курьера без логина")
    public void canNotCreateCourierWithoutLogIn(){
        courier.setLogin(null);
        ValidatableResponse courierResponse = client.createCourier(courier);
        int statusCodeWithoutLogIn = courierResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_BAD_REQUEST, statusCodeWithoutLogIn); //ожидаемый код ответа 400
        String actualMessage = courierResponse.extract().path("message"); //получене актуального текста сообщения
        Assert.assertEquals("Недостаточно данных для создания учетной записи", actualMessage); //проверка текста сообщения
    }
    @Test
    @DisplayName("Создание курьера без пароля")
    public void canNotCreateCourierWithoutPassword(){
        courier.setPassword(null);
        ValidatableResponse courierResponse = client.createCourier(courier);
        int statusCodeWithoutPass = courierResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_BAD_REQUEST, statusCodeWithoutPass); //ожидаемый код ответа 400
        String actualMessage = courierResponse.extract().path("message"); //получене актуального текста сообщения
        Assert.assertEquals("Недостаточно данных для создания учетной записи", actualMessage); //проверка текста сообщения
    }
 }
