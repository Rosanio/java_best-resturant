import org.sql2o.*;
import java.util.List;

public class Review {
  private int id;
  private String name;
  private String review;
  private int rating;

  public Review (String name, String review, int rating) {
    this.name = name;
    this.review = review;
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getReview() {
    return review;
  }

  public int getRating() {
    return rating;
  }

  @Override
  public boolean equals(Object otherReview){
    if (!(otherReview instanceof Review)) {
      return false;
    } else {
      Review newReview = (Review) otherReview;
      return this.getName().equals(newReview.getName()) &&
        this.getId() == newReview.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO reviews (name, review, rating) VALUES (:name, :review, :rating)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("review", review)
        .addParameter("rating", rating)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Review> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews";
      return con.createQuery(sql)
      .executeAndFetch(Review.class);
    }
  }

  public void update(String newName) {
    this.name = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE reviews SET name = :newName WHERE id = :id";
      con.createQuery(sql).addParameter("newName", newName).addParameter("id", id).executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM reviews WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM reviews WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Review.class);
    }
  }
}
