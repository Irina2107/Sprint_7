package courier;
import com.example.courier.CourierAssertions;
import com.example.courier.CourierClient;
import com.example.courier.CourierGenerator;
import com.example.courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateCourierPosTest extends CourierAssertions{
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    protected int courierId;

    @After
    @DisplayName("Удаление курьера по id")
    public void deleteCourier() {
        if(courierId !=0) {
            var createResponseDel =  client.deleteCourier(courierId); //удаление курьера
            int statusCodeDel = createResponseDel.extract().statusCode(); //получение кода ответа
            Assert.assertEquals(SC_OK, statusCodeDel); // проверка, что код ответа 200
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void courierCreateSuccess() {
        var courier = generator.random();
        var createResponseCr = client.createCourier(courier);
        int statusCodeCr = createResponseCr.extract().statusCode(); // получение кода ответа
        Assert.assertEquals(SC_CREATED, statusCodeCr); //проверка, что код ответа 201
        courierId = client.logIn(Credentials.from(courier)).extract().path("id"); // получение id

    }
}

