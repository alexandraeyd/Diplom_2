package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UpdateUserTest {
    UserClient userClient;
    User user;


    @Before
    public void setUp(){
        userClient = new UserClient();
        user = DataGenerator.getRandomUser();
        ValidatableResponse loginResponse = userClient.register(user);
        String accessToken = loginResponse.extract().path("accessToken");
        String refreshToken = loginResponse.extract().path("refreshToken");
    }

//    @Test
//    // @DisplayName("Check 201 status code and response for creating courier")
//    public void userCanLogInWithValidCredentials(){
//        ValidatableResponse createResponse = userClient.login(user);
//        int statusCode = createResponse.extract().statusCode();
//        assertEquals("User is not logged in", statusCode, SC_OK);
//
//    }
}
