package praktikum;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class GetUserOrdersTest {

    OrderClient orderClient;
    UserClient userClient;
    User user;
    String accessToken;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse loginResponse = userClient.register(user);
        accessToken = loginResponse.extract().path("accessToken");
        accessToken = accessToken.substring(7);
    }

    @Test
    @DisplayName("Check that orders can be returned for valid user")
    public void ordersOfUserCanBeReturnedWithToken(){

        ValidatableResponse response = orderClient.getOrdersWithToken(accessToken);
        int statusCode = response.extract().statusCode();

        assertEquals("Orders are not returned", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Check that orders can not be returned without valid token")
    public void ordersOfUserCanNotBeReturnedWithoutToken(){

        ValidatableResponse response = orderClient.getOrdersWithoutToken();
        int statusCode = response.extract().statusCode();

        assertEquals("Orders are returned without token", SC_UNAUTHORIZED, statusCode);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken);
    }
}
