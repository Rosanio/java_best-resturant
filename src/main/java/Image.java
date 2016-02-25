import org.sql2o.*;
import java.util.List;

public class Image {
  private int id;
  private String url;
  private int restaurant_id;

  public Image (String url, int restaurant_id) {
    this.url = url;
    this.restaurant_id = restaurant_id;
  }

  public int getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public int getRestaurantId() {
    return restaurant_id;
  }

  @Override
  public boolean equals(Object otherImage){
    if (!(otherImage instanceof Image)) {
      return false;
    } else {
      Image newImage = (Image) otherImage;
      return this.getUrl().equals(newImage.getUrl()) &&
        this.getId() == newImage.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO images (url, restaurant_id) VALUES (:url, :restaurant_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("url", url)
        .addParameter("restaurant_id", restaurant_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Image> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM images";
      return con.createQuery(sql)
      .executeAndFetch(Image.class);
    }
  }

  public void update(String newUrl) {
    this.url = newUrl;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE images SET url = :newUrl WHERE id = :id";
      con.createQuery(sql).addParameter("newUrl", newUrl).addParameter("id", id).executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM images WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public static Image find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM images WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Image.class);
    }
  }
}
