import org.sql2o.*;
import java.util.List;

public class User {
  private int id;
  private String username;
  private String password;
  private String permission;

  public User (String username, String password, String permission) {
    this.username = username;
    this.password = password;
    this.permission = permission;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getPermission() {
    return permission;
  }

  @Override
  public boolean equals(Object otherUser){
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getUsername().equals(newUser.getUsername()) &&
        this.getId() == newUser.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (username, password, permission) VALUES (:username, :password, :permission)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", username)
        .addParameter("password", password)
        .addParameter("permission", permission)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<User> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users";
      return con.createQuery(sql)
      .executeAndFetch(User.class);
    }
  }

  public void update(String newPermission) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET permission = :newPermission WHERE id = :id";
      con.createQuery(sql).addParameter("newPermission", newPermission).addParameter("id", id).executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(User.class);
    }
  }

  public static User search(String username) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE username = :username";
      return con.createQuery(sql).addParameter("username", username).executeAndFetchFirst(User.class);
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE user_id = :id";
      return con.createQuery(sql).addParameter("id", id)
      .executeAndFetch(Review.class);
    }
  }
}
