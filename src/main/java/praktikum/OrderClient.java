package praktikum;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import java.util.List;



public class OrderClient extends BurgerRestClient{

    private static final String USER_PATH = "orders";
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

  @Step("Send POST request to /api/orders with token")
    public ValidatableResponse createWithToken(Order order, String token){

        String path = USER_PATH;
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(path)
                .then();

    }

    @Step("Send POST request to /api/orders without token")
    public ValidatableResponse createWithoutToken(Order order, String token){

        String path = USER_PATH;
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(path)
                .then();

    }

   @Step("Send GET request to /api/orders")
    public ValidatableResponse getOrdersWithToken(String token){

        String path = USER_PATH;
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(path)
                .then();

    }

    @Step("Send GET request to /api/orders without token")
    public ValidatableResponse getOrdersWithoutToken(){

        String path = USER_PATH;
        return given()
                .spec(getBaseSpec())
                .when()
                .get(path)
                .then();

    }
}
