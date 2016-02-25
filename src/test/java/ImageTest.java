import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ImageTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Image.all().size(), 0);
  }

  @Test
  public void save_savesImageToDatabase() {
    Image newImage = new Image("Matt's", 1);
    newImage.save();
    assertTrue(Image.all().get(0).equals(newImage));
  }

  @Test
  public void update_updatesNameOfImage() {
    Image newImage = new Image("Matt's", 1);
    newImage.save();
    String newName = "Anna's";
    newImage.update(newName);
    assertTrue(Image.all().get(0).getUrl().equals(newName));
  }

  @Test
  public void delete_removesImageFromDatabase() {
    Image newImage = new Image("Matt's", 1);
    newImage.save();
    newImage.delete();
    assertEquals(Image.all().size(), 0);
  }

  @Test
  public void find_returnImageWithSameId() {
    Image firstImage = new Image("Matt's", 1);
    firstImage.save();
    Image secondImage = new Image("Anna's", 1);
    secondImage.save();
    assertTrue(Image.find(secondImage.getId()).equals(secondImage));
  }
}
