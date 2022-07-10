package praktikum;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends BurgerRestClient{

    private static final String USER_PATH = "orders/";
    private List<String> actualIngredients;


    public OrderClient() {

        String path = "ingredients";
        actualIngredients = given()
                .spec(getBaseSpec())
                .when()
                .get(path)
                .jsonPath()
                .getList("data._id");

    }

    public List<String> getActualIngredients() {
        return actualIngredients;
    }

    public ValidatableResponse createWithAuth (Order order, String token){

        String path = USER_PATH;
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(path)
                .then();

    }
}
