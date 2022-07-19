package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.*;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import io.qameta.allure.junit4.DisplayName;
import praktikum.client.OrderClient;
import praktikum.client.UserClient;
import praktikum.models.Order;
import praktikum.models.User;
import praktikum.utils.DataGenerator;


public class CreateOrderTest {

    private static OrderClient orderClient;
    private static Order order;
    private static UserClient userClient;
    private static User user;
    private static String accessToken;


    @BeforeClass
    public static void setUp(){
        orderClient = new OrderClient();
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse loginResponse = userClient.register(user);
        accessToken = loginResponse.extract().path("accessToken");
        accessToken = accessToken.substring(7);
    }


    @Test
    @DisplayName("Check that order can be created for valid user")
    public void orderCanBeCreatedWithToken(){

        order = DataGenerator.getRandomOrder();

        ValidatableResponse response = orderClient.createWithToken(order, accessToken);
        int statusCode = response.extract().statusCode();
        assertEquals("Order is not created", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Check that order can not be created with invalid token")
    public void orderCanNotBeCreatedWithoutToken(){

        ValidatableResponse response = orderClient.createWithoutToken(order);
        int statusCode = response.extract().statusCode();

        assertNotEquals("Order is created without authentification",  SC_BAD_REQUEST, statusCode);
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

    @AfterClass
    public static void tearDown(){
        userClient.delete(accessToken);
    }

}
