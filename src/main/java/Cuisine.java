import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private int cuisine_id;
  private String type;

  public Cuisine (String type) {
    this.type = type;
  }

  public int getId() {
    return cuisine_id;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object otherCuisine){
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.getType().equals(newCuisine.getType()) &&
        this.getId() == newCuisine.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine (type) VALUES (:type)";
      this.cuisine_id = (int) con.createQuery(sql, true)
        .addParameter("type", type)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Cuisine> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine";
      return con.createQuery(sql)
      .executeAndFetch(Cuisine.class);
    }
  }

  //UPDATE
  public void update(String newType) {
    this.type = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisine SET type = :newType WHERE cuisine_id = :cuisine_id";
      con.createQuery(sql).addParameter("newType", newType).addParameter("cuisine_id", cuisine_id).executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisine WHERE cuisine_id = :cuisine_id";
      con.createQuery(sql).addParameter("cuisine_id", cuisine_id).executeUpdate();
    }
  }

  public static Cuisine find(int cuisine_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cuisine WHERE cuisine_id = :cuisine_id";
      return con.createQuery(sql).addParameter("cuisine_id", cuisine_id).executeAndFetchFirst(Cuisine.class);
    }
  }

  public List<Restaurant> getRestaurants() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants WHERE cuisineid = :cuisine_id";
      return con.createQuery(sql).addParameter("cuisine_id", cuisine_id)
      .executeAndFetch(Restaurant.class);
    }
  }

  public void deleteRestaurants() {
    List<Restaurant> restaurants = this.getRestaurants();
    for(Restaurant restaurant : restaurants) {
      restaurant.deleteReviews();
    }
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE cuisineid = :cuisine_id";
      con.createQuery(sql).addParameter("cuisine_id", cuisine_id)
      .executeUpdate();
    }
  }

}
