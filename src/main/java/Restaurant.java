import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int id;
  private String name;
  private int cuisineId;

  public Restaurant (String name, int cuisineId) {
    this.name = name;
    this.cuisineId = cuisineId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCuisineId() {
    return cuisineId;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, cuisineId) VALUES (:name, :cuisineId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("cuisineId", cuisineId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Restaurant> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants";
      return con.createQuery(sql)
      .executeAndFetch(Restaurant.class);
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name = :newName WHERE id = :id";
      con.createQuery(sql).addParameter("newName", newName).addParameter("id", id).executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Restaurant.class);
    }
  }

  public String getCuisineType() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine WHERE cuisine_id = :cuisineid";
      return con.createQuery(sql).addParameter("cuisineid", cuisineId).executeAndFetchFirst(Cuisine.class).getType();
    }
  }

  public List<Review> getReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE restaurant_id = :id";
      return con.createQuery(sql).addParameter("id", id)
      .executeAndFetch(Review.class);
    }
  }

  public void deleteReviews() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM reviews WHERE restaurant_id = :id";
      con.createQuery(sql).addParameter("id", id)
      .executeUpdate();
    }
  }

  public List<Image> getImages() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM images WHERE restaurant_id = :id";
      return con.createQuery(sql).addParameter("id", id)
      .executeAndFetch(Image.class);
    }
  }
}
