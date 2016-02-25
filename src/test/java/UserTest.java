import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

import java.util.*;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(User.all().size(), 0);
  }

  @Test
  public void save_savesUserToDatabase() {
    User newUser = new User("Matt", "password", "user");
    newUser.save();
    assertTrue(User.all().get(0).equals(newUser));
  }

  @Test
  public void update_updatesUsernameOfUser() {
    User newUser = new User("Matt", "password", "user");
    newUser.save();
    String newUsername = "Anna";
    newUser.update(newUsername);
    assertTrue(User.all().get(0).getUsername().equals(newUsername));
  }

  @Test
  public void delete_removesUserFromDatabase() {
    User newUser = new User("Matt", "password", "user");
    newUser.save();
    newUser.delete();
    assertEquals(User.all().size(), 0);
  }

  @Test
  public void find_returnUserWithSameId() {
    User firstUser = new User("Matt", "password", "user");
    firstUser.save();
    User secondUser = new User("Anna", "1234", "admin");
    secondUser.save();
    assertTrue(User.find(secondUser.getId()).equals(secondUser));
  }

  @Test
  public void search_returnUserWithSameName() {
    User firstUser = new User("Matt", "password", "user");
    firstUser.save();
    assertTrue(User.search(firstUser.getUsername()).equals(firstUser));
  }

  @Test
  public void all_returnsListOfAllUsers() {
    User firstUser = new User("Matt", "password", "user");
    firstUser.save();
    User secondUser = new User("Anna", "1234", "admin");
    secondUser.save();
    User[] users = new User[] { firstUser, secondUser };
    assertTrue(User.all().containsAll(Arrays.asList(users)));
  }
}
