package praktikum.client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.client.BurgerRestClient;
import praktikum.models.Order;

import static io.restassured.RestAssured.given;
import java.util.List;



public class OrderClient extends BurgerRestClient {

    private static final String USER_PATH = "orders";
    private List<String> actualIngredients;
    private final String INGREDIENTS_PATH = "ingredients";


    public OrderClient() {


        actualIngredients = given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .jsonPath()
                .getList("data._id");

    }

    public List<String> getActualIngredients() {
        return actualIngredients;
    }

  @Step("Send POST request to /api/orders with token")
    public ValidatableResponse createWithToken(Order order, String token){

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(USER_PATH)
                .then();

    }

    @Step("Send POST request to /api/orders without token")
    public ValidatableResponse createWithoutToken(Order order){

        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(USER_PATH)
                .then();

    }

   @Step("Send GET request to /api/orders")
    public ValidatableResponse getOrdersWithToken(String token){

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(USER_PATH)
                .then();

    }

    @Step("Send GET request to /api/orders without token")
    public ValidatableResponse getOrdersWithoutToken(){

        return given()
                .spec(getBaseSpec())
                .when()
                .get(USER_PATH)
                .then();

    }
}
