package praktikum;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class UserClient extends BurgerRestClient{

    private static final String USER_PATH = "auth/";

    @Step("Send POST request to /api/auth/register")
    public ValidatableResponse register (User user){

        String path = USER_PATH +"register";
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(path)
                .then();

    }

    @Step("Send POST request to /api/auth/login")
    public ValidatableResponse login (User user){

        String path = USER_PATH +"login";
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(path)
                .then();

    }

    @Step("Send PATCH request to /api/auth/user with token")
    public ValidatableResponse updateWithToken (User user, String token){

        String path = USER_PATH +"user";
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(user)
                .when()
                .patch(path)
                .then();

    }

    @Step("Send PATCH request to /api/auth/user without token")
    public ValidatableResponse updateWithoutToken (User user){

        String path = USER_PATH +"user";
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(path)
                .then();

    }

    @Step("Send DELETE request to /api/auth/user")
    public ValidatableResponse delete(String token){

        String path = USER_PATH +"user";
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .delete()
                .then();

    }
}
