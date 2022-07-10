package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CreateOrderTest {

    OrderClient orderClient;
    Order order;
    UserClient userClient;
    User user;
    String accessToken;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        order = DataGenerator.getRandomOrder();
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse loginResponse = userClient.register(user);
        accessToken = loginResponse.extract().path("accessToken");
    }


    @Test
    public void orderCanBeCreated(){

        ValidatableResponse createResponse = orderClient.createWithAuth(order, accessToken);
        int statusCode = createResponse.extract().statusCode();
//String msg = createResponse.extract().path("message");
        assertEquals("Order is not created", statusCode, SC_OK);
    }
}
