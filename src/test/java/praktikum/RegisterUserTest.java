package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.DataGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;


public class RegisterUserTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Check that new user can be registered")
    public void userCanBeRegistered(){
        user = DataGenerator.getRandomUser();
        ValidatableResponse response = userClient.register(user);
        int statusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");
        accessToken = accessToken.substring(7);

        userClient.delete(accessToken);

        assertEquals("User is not registered",  SC_OK, statusCode);
    }



    @Test
    @DisplayName("Check that existed user can not be registered")
    public void existedUserCanNotBeRegistered(){
        user = DataGenerator.getRandomUser();
        userClient.register(user);
        user.setName(user.getName()+"new");
        user.setPassword(user.getPassword()+"new");

        ValidatableResponse response = userClient.register(user);;
        int statusCode = response.extract().statusCode();

        assertEquals("User with existed email is registered",  SC_FORBIDDEN, statusCode);
    }

    @Test
    @DisplayName("Check that user can not be registered without name")
    public void userWithoutNAmeCanNotBeRegistered(){
        user = DataGenerator.getRandomCourierWithNullName();
        ValidatableResponse response = userClient.register(user);
        int statusCode = response.extract().statusCode();

        assertEquals("User is registered without name",  SC_FORBIDDEN, statusCode);
    }


}
