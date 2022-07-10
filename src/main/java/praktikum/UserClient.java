package praktikum;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends BurgerRestClient{

    private static final String USER_PATH = "auth/";

   // @Step ("Create courier")
    public ValidatableResponse register (User user){

        String path = USER_PATH +"register";
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(path)
                .then();

    }

    public ValidatableResponse login (User user){

        String path = USER_PATH +"login";
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(path)
                .then();

    }

//    public ValidatableResponse update (User user, String token){
//
//        String path = USER_PATH +"user";
//        return given()
//                .spec(getBaseSpec())
//                .auth().oauth2(token)
//                .body(user)
//                .when()
//                .post(path)
//                .then();
//
//    }
}
