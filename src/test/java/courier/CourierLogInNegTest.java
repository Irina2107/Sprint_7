package courier;
import com.example.courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierLogInNegTest {
    private CourierClient client;
    private CourierAssertions check;
    protected int courierId;
    private Courier courier;
    Faker faker;
    @Before
        public void setUp() {
        CourierGenerator generator = new CourierGenerator();
        client = new CourierClient();
        check = new CourierAssertions();
        faker = new Faker();
        courier = generator.random();
        client.createCourier(courier);
        courierId = client.logIn(Credentials.from(courier)).extract().path("id");
    }
    @After
    @DisplayName("Удаление курьера по id")
    public void deleteCourier() {
        if(courierId !=0) {
            var createResponseDel =  client.deleteCourier(courierId);
            int statusCodeDel = createResponseDel.extract().statusCode();
            Assert.assertEquals(SC_OK, statusCodeDel);
        }
    }
    @Test
    @DisplayName("система вернёт ошибку, если неправильно указать логин")
    public void courierLogInWithErrorLogIn(){
        courier.setLogin(faker.bothify("10"));
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse); //проверка, что не смогли залогиниться
        int statusCodeErrorLogIn = loginResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_NOT_FOUND, statusCodeErrorLogIn); //проверка, что код ответа 404
    }
    @Test
    @DisplayName("система вернёт ошибку, если неправильно указать пароль")
    public void courierLogInErrorPassword(){
        courier.setPassword(faker.number().toString());
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse); //проверка, что не смогли залогиниться
        int statusCodeErrorPassword = loginResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_NOT_FOUND, statusCodeErrorPassword); //проверка, что код ответа 404

    }
    @Test
    @DisplayName("система вернёт ошибку, если не запнять поле пароль")
    public void courierLogInErrorWithoutPasswordField(){
        courier.setPassword("");
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithoutCredentials(loginResponse); //проверка, что не смогли залогиниться
        int statusCodeWithoutPassword = loginResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_BAD_REQUEST, statusCodeWithoutPassword); //проверка, что код ответа 400
    }
    @Test
    @DisplayName("система вернёт ошибку, если не заполнять поле login")
    public void courierLogInErrorWithoutLogInField(){
        courier.setLogin("");
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithoutCredentials(loginResponse);
        int statusCodeErrorWithoutLogIn = loginResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_BAD_REQUEST, statusCodeErrorWithoutLogIn ); //проверка, что код ответа 400
    }
    @Test
    @DisplayName("система вернёт ошибку, если авторизоваться под несуществующим пользователем")
    public void courierLogInErrorNullUser(){
        courier.setPassword(faker.number().toString());
        courier.setLogin(faker.name().firstName());
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse);
        int statusCodeErrorNullUser = loginResponse.extract().statusCode(); //получение кода ответа
        Assert.assertEquals(HTTP_NOT_FOUND, statusCodeErrorNullUser); //проверка, что код ответа 404
    }
}
