import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/welcome", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String enteredUsername = request.queryParams("username");
      String enteredPassword = request.queryParams("password");
      User newUser = User.search(enteredUsername);
      model.put("enteredPassword", enteredPassword);
      model.put("user", newUser);
      model.put("template", "templates/welcome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/signup", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/signup.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String enteredUsername = request.queryParams("username");
      String enteredPassword = request.queryParams("password");
      User newUser = new User(enteredUsername, enteredPassword, "user");
      newUser.save();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:userId/adminHome", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("cuisines", Cuisine.all());
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/adminHome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/:userId/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String userCuisine = request.queryParams("type");
      Cuisine newCuisine = new Cuisine(userCuisine);
      newCuisine.save();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:userId/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:userId/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/restaurants.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/:userId/adminHome", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String userRestaurant = request.queryParams("name");
      Restaurant newRestaurant = new Restaurant(userRestaurant, Integer.parseInt(request.queryParams("cuisineSelect")));
      newRestaurant.save();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("restaurants", Restaurant.all());
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/adminHome.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:userId/cuisines/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("restaurants", cuisine.getRestaurants());
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:userId/:id", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params(":id")));
      User user = User.find(Integer.parseInt(request.params(":userId")));
      model.put("user", user);
      model.put("restaurant", restaurant);
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
