import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

import java.util.*;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void save_savesCuisineToDatabase() {
    Cuisine newCuisine = new Cuisine("Matt's");
    newCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(newCuisine));
  }

  @Test
  public void update_updatesNameOfCuisine() {
    Cuisine newCuisine = new Cuisine("Matt's");
    newCuisine.save();
    String newName = "Anna's";
    newCuisine.update(newName);
    assertTrue(Cuisine.all().get(0).getType().equals(newName));
  }

  @Test
  public void delete_removesCuisineFromDatabase() {
    Cuisine newCuisine = new Cuisine("Matt's");
    newCuisine.save();
    newCuisine.delete();
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void find_returnCuisineWithSameId() {
    Cuisine firstCuisine = new Cuisine("Matt's");
    firstCuisine.save();
    Cuisine secondCuisine = new Cuisine("Anna's");
    secondCuisine.save();
    assertTrue(Cuisine.find(secondCuisine.getId()).equals(secondCuisine));
  }

  @Test
  public void getRestaurants_returnsAllRestaurantsForCuisineType() {
    Cuisine firstCuisine = new Cuisine("Italian");
    firstCuisine.save();
    Restaurant firstRestaurant = new Restaurant("Rosanio's", firstCuisine.getId());
    firstRestaurant.save();
    Restaurant secondRestaurant = new Restaurant("Rico's", firstCuisine.getId());
    secondRestaurant.save();
    Restaurant[] restaurants = new Restaurant[] { firstRestaurant, secondRestaurant };
    assertTrue(firstCuisine.getRestaurants().containsAll(Arrays.asList(restaurants)));
  }
}
