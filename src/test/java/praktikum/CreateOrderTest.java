package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import io.qameta.allure.junit4.DisplayName;


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
        accessToken = accessToken.substring(7);
    }


    @Test
    @DisplayName("Check that order can be created for valid user")
    public void orderCanBeCreatedWithToken(){

        ValidatableResponse response = orderClient.createWithToken(order, accessToken);
        int statusCode = response.extract().statusCode();

        assertEquals("Order is not created", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Check that order can not be created with invalid token")
    public void orderCanNotBeCreatedWithToken(){

        ValidatableResponse response = orderClient.createWithoutToken(order, accessToken);
        int statusCode = response.extract().statusCode();

        assertNotEquals("Order is created without authentification",  SC_OK, statusCode);
    }

    @Test
    @DisplayName("Check that order can not be created with invalid ingredient")
    public void orderCanNotBeCreatedWithInvalidIngredient(){
        order = DataGenerator.getRandomOrderWithInvalidIngredient();
        ValidatableResponse response = orderClient.createWithToken(order, accessToken);
        int statusCode = response.extract().statusCode();

        assertEquals("Order is created without authentification", SC_INTERNAL_SERVER_ERROR,  statusCode);
    }

    @Test
    @DisplayName("Check that order can not be created without ingredients")
    public void orderCanNotBeCreatedWithoutIngredients(){
        order = DataGenerator.getEmptyOrder();
        ValidatableResponse response = orderClient.createWithToken(order, accessToken);
        int statusCode = response.extract().statusCode();

        assertEquals("Order is created without ingredients", SC_BAD_REQUEST, statusCode);
    }

    @After
    public void tearDown(){
        userClient.delete(accessToken);
    }

}
