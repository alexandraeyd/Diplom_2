package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class LoginUserTest {

    UserClient userClient;
    User user;


    @Before
    public void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
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
        user.setPassword("newpassword");
        ValidatableResponse response = userClient.login(user);
        int statusCode = response.extract().statusCode();
        assertEquals("User logged in with wrong password", SC_UNAUTHORIZED, statusCode);

    }
}


