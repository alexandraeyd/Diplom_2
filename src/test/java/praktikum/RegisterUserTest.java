package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterUserTest {

    UserClient userClient;
    User user;
    int userId;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
    }

    @Test
   // @DisplayName("Check 201 status code and response for creating courier")
    public void userCanBeRegistered(){
        user = DataGenerator.getRandomUser();
        ValidatableResponse createResponse = userClient.register(user);
        int statusCode = createResponse.extract().statusCode();

        assertEquals("User is not registered", statusCode, SC_OK);
    }

    @Test
    public void existedUserCanNotBeRegistered(){
        user = DataGenerator.getRandomUser();
        userClient.register(user);
        user.setName(user.getName()+"new");
        user.setPassword(user.getPassword()+"new");

        ValidatableResponse createResponse = userClient.register(user);
        int statusCode = createResponse.extract().statusCode();

        assertEquals("User with existed email is registered", statusCode, SC_FORBIDDEN);
    }

    @Test
    // @DisplayName("Check 201 status code and response for creating courier")
    public void userWithoutNAmeCanNotBeRegistered(){
        user = DataGenerator.getRandomCourierWithNullName();
        ValidatableResponse createResponse = userClient.register(user);
        int statusCode = createResponse.extract().statusCode();

        assertEquals("User is not registered", statusCode, SC_FORBIDDEN);
    }
}
