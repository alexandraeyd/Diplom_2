package praktikum;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataGenerator {

    public static User getRandomUser() {
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userPassword = RandomStringUtils.randomAlphabetic(10);
        String userEmail = RandomStringUtils.randomAlphabetic(10)+"@gmail.com";
        return new User(userName,userPassword,userEmail);
    }

    public static User getRandomCourierWithNullName(){
        String userName= null;
        String userPassword= RandomStringUtils.randomAlphabetic(10);
        String userEmail = RandomStringUtils.randomAlphabetic(10);
        return new User(userName,userPassword,userEmail);
    }

    public static Order getRandomOrder() {
        OrderClient orderClient = new OrderClient();
        List<String> orderIngredients = new ArrayList<String>();
        int maxIngredients = orderClient.getActualIngredients().size();
        Random rn = new Random();
        for (int i = 0; i < maxIngredients; i++) {
            if (rn.nextBoolean()) {
                orderIngredients.add(orderClient.getActualIngredients().get(i));
            }
        }
        return new Order(orderIngredients);
    }

    public static Order getRandomOrderWithInvalidIngredient() {
        OrderClient orderClient = new OrderClient();
        List<String> orderIngredients = new ArrayList<String>();
        int maxIngredients = orderClient.getActualIngredients().size();
        Random rn = new Random();
        for (int i = 0; i < maxIngredients; i++) {
            if (rn.nextBoolean()) {
                orderIngredients.add(orderClient.getActualIngredients().get(i));
            }
        }
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        return new Order(orderIngredients);
    }

    public static Order getEmptyOrder() {
        OrderClient orderClient = new OrderClient();
        List<String> orderIngredients = new ArrayList<String>();
        return new Order(orderIngredients);
    }
}
