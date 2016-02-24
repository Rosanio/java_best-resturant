import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ReviewTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Review.all().size(), 0);
  }

  @Test
  public void save_savesReviewToDatabase() {
    Review newReview = new Review("Matt", "password", 1);
    newReview.save();
    assertTrue(Review.all().get(0).equals(newReview));
  }

  @Test
  public void update_updatesReviewnameOfReview() {
    Review newReview = new Review("Matt", "password", 1);
    newReview.save();
    String newReviewname = "Anna";
    newReview.update(newReviewname);
    assertTrue(Review.all().get(0).getName().equals(newReviewname));
  }

  @Test
  public void delete_removesReviewFromDatabase() {
    Review newReview = new Review("Matt", "password", 1);
    newReview.save();
    newReview.delete();
    assertEquals(Review.all().size(), 0);
  }

  @Test
  public void find_returnReviewWithSameId() {
    Review firstReview = new Review("Matt", "password", 1);
    firstReview.save();
    Review secondReview = new Review("Matt", "password", 1);
    secondReview.save();
    assertTrue(Review.find(secondReview.getId()).equals(secondReview));
  }
}
