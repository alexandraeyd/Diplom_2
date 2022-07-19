package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import praktikum.client.UserClient;
import praktikum.models.User;
import praktikum.utils.DataGenerator;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class UpdateUserTest {
    UserClient userClient;
    User user;
    String accessToken;


    @Before
    public void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse response = userClient.register(user);
        accessToken = response.extract().path("accessToken");
        accessToken = accessToken.substring(7);
    }

    @Test
    @DisplayName("User can be updated with token")
    public void userCanBeUpdatedWithToken(){
        user = DataGenerator.getRandomUser();
        ValidatableResponse response = userClient.updateWithToken(user, accessToken);
        int statusCode = response.extract().statusCode();
        String msg = response.extract().path("message");
        assertEquals("User is not updated",  SC_OK, statusCode);

    }

    @Test
    @DisplayName("User can not be updated without token")
    public void userCanNotBeUpdatedWithoutToken(){
        user = DataGenerator.getRandomUser();
        ValidatableResponse response = userClient.updateWithoutToken(user);
        int statusCode = response.extract().statusCode();
        assertEquals("User is updated without token",  SC_UNAUTHORIZED, statusCode);

    }

    @After
    public void tearDown(){
        userClient.delete(accessToken);
    }
}
