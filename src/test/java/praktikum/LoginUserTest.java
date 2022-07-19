package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import praktikum.client.OrderClient;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.DataGenerator;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class LoginUserTest {

    private static UserClient userClient;
    private static User user;
    private static String accessToken;


    @BeforeClass
    public static void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse loginResponse = userClient.register(user);
        accessToken = loginResponse.extract().path("accessToken");
        accessToken = accessToken.substring(7);
    }


    @Test
    @DisplayName("Check that user can login with valid credentials")
    public void userCanLogInWithValidCredentials(){
        ValidatableResponse response = userClient.login(user);
        int statusCode = response.extract().statusCode();
        assertEquals("User is not logged in", SC_OK, statusCode);

    }

    @Test
    @DisplayName("Check that user can not login with invalid password")
    public void userWithWrongPasswordCanNotLogIn(){
        User userInvalidPass = DataGenerator.getRandomUser();
        userInvalidPass.setName(user.getName());
        userInvalidPass.setPassword("newpassword");
        ValidatableResponse response = userClient.login(userInvalidPass);
        int statusCode = response.extract().statusCode();
        assertEquals("User logged in with wrong password", SC_UNAUTHORIZED, statusCode);

    }

    @AfterClass
    public static void tearDown(){
        userClient.delete(accessToken);
    }
}




