import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void save_savesRestaurantToDatabase() {
    Restaurant newRestaurant = new Restaurant("Matt's", 1);
    newRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(newRestaurant));
  }

  @Test
  public void update_updatesNameOfRestaurant() {
    Restaurant newRestaurant = new Restaurant("Matt's", 1);
    newRestaurant.save();
    String newName = "Anna's";
    newRestaurant.update(newName);
    assertTrue(Restaurant.all().get(0).getName().equals(newName));
  }

  @Test
  public void delete_removesRestaurantFromDatabase() {
    Restaurant newRestaurant = new Restaurant("Matt's", 1);
    newRestaurant.save();
    newRestaurant.delete();
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void find_returnRestaurantWithSameId() {
    Restaurant firstRestaurant = new Restaurant("Matt's", 1);
    firstRestaurant.save();
    Restaurant secondRestaurant = new Restaurant("Anna's", 1);
    secondRestaurant.save();
    assertTrue(Restaurant.find(secondRestaurant.getId()).equals(secondRestaurant));
  }

  @Test
  public void getCuisineType_returnTypeOfCuisineAssignedToRestaurant() {
    Cuisine lasagna = new Cuisine("Fish");
    lasagna.save();
    Restaurant firstRestaurant = new Restaurant("Matt's", lasagna.getId());
    firstRestaurant.save();
    assertTrue(firstRestaurant.getCuisineType().equals(lasagna.getType()));
  }
}
