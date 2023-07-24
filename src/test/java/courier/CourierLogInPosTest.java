package courier;
import com.example.courier.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierLogInPosTest {
    private final CourierGenerator generator = new CourierGenerator();
    private CourierClient client;
    private Courier courier;
    private Integer courierId;

  @Before
      public void setUp() {
      client = new CourierClient();
      courier = generator.random();
      client.createCourier(courier);
    }
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
    @DisplayName("Курьер может авторизоваться, для авторизации нужно передать все обязательные поля, успешный запрос возвращает id")
    public void courierLogInSuccess(){
        courierId = client.logIn(Credentials.from(courier)).extract().path("id"); // вход в систему и получение id
        Assert.assertNotNull(courierId); // проверка, что id курьера не равно нулю
     }
}
