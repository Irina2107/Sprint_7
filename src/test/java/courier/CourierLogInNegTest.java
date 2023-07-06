package courier;

import com.example.courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;

public class CourierLogInNegTest {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertions check = new CourierAssertions();
    protected int courierId;
    private Courier courier;


    Faker faker = new Faker();
    @Before
    public void setUp() {
        courier = generator.random();
        client.createCourier(courier);
    }
    @After
    public void deleteCourier() {
        client.deleteCourier(courierId);
    }


    @Test
    @DisplayName("система вернёт ошибку, если неправильно указать логин")
    public void courierLogInWithErrorLogIn(){
        courier.setLogin(faker.bothify("10"));
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse);
        System.out.println();
    }

    @Test
    @DisplayName("система вернёт ошибку, если неправильно указать пароль")
    public void courierLogInErrorPassword(){
        courier.setPassword(faker.number().toString());
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse);
    }
    @Test
    @DisplayName("система вернёт ошибку, если нет поля пароль")
    public void courierLogInErrorWithoutPasswordField(){
        courier.setPassword("");
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithoutCredentials(loginResponse);
    }

    @Test
    @DisplayName("система вернёт ошибку, если нет поля login")
    public void courierLogInErrorWithoutLogInField(){
        courier.setLogin("");
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithoutCredentials(loginResponse);
    }
    @Test
    @DisplayName("система вернёт ошибку, если авторизоваться под несуществующим пользователем")
    public void courierLogInErrorNullUser(){
        courier.setPassword(faker.number().toString());
        courier.setLogin(faker.name().firstName());
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.canNotLogInWithNonexistentCredentials(loginResponse);

    }

}
