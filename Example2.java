import Backend.Recipe;
import Backend.Runtime;
import java.util.ArrayList;

public class Example2 {

  public static void main(String[] args) {
    Runtime runtime = new Runtime();
    //Starting out this example by setting all ingredients to available.
    for (int i = 0; i < runtime.ingredientCount(); i++) {
      String name = runtime.getIngredient(i).getName();
      try {
        runtime.setAvailable(name);
      } catch (Exception e) {
        System.out.println("An error has occured in saving to disk.");
        System.exit(-1);
      }
    }

    System.out.println("First test - all active, 0 missing");
    ArrayList<Recipe> canMake = runtime.getRecipes(0);
    for (int i = 0; i < canMake.size(); i++) {
      Recipe rec = canMake.get(i);
      int ingredientCount = rec.getIngredientCount();
      System.out.println(rec.getName());
      System.out.println("Rating: " + rec.getAverageRating());
      System.out.println("Prep Time: " + rec.getPrepTime());
      System.out.println("Serves: " + rec.getServingCount());
      System.out.println("Ingredients:");
      for (int j = 0; j < ingredientCount; j++) {
        System.out.println(
          " -  " +
          rec.getIngredient(j).getIngredientName() +
          " - " +
          rec.getIngredient(j).getAmount()
        );
      }
    }

    System.out.println();
    System.out.println("Second test - all active, 1 missing");

    canMake = runtime.getRecipes(1);
    for (int i = 0; i < canMake.size(); i++) {
      Recipe rec = canMake.get(i);
      int ingredientCount = rec.getIngredientCount();
      System.out.println(rec.getName());
      System.out.println("Rating: " + rec.getAverageRating());
      System.out.println("Prep Time: " + rec.getPrepTime());
      System.out.println("Serves: " + rec.getServingCount());
      System.out.println("Ingredients:");
      for (int j = 0; j < ingredientCount; j++) {
        System.out.println(
          " -  " +
          rec.getIngredient(j).getIngredientName() +
          " - " +
          rec.getIngredient(j).getAmount()
        );
      }
    }

    System.out.println();
    System.out.println("Third test - 1 inactive, 0 missing");

    try {
      runtime.setUnAvailable(runtime.getIngredient(1).getName());
    } catch (Exception e) {
      System.out.println("An error has occured in saving to disk.");
      System.exit(-1);
    }

    canMake = runtime.getRecipes(0);
    for (int i = 0; i < canMake.size(); i++) {
      Recipe rec = canMake.get(i);
      int ingredientCount = rec.getIngredientCount();
      System.out.println(rec.getName());
      System.out.println("Rating: " + rec.getAverageRating());
      System.out.println("Prep Time: " + rec.getPrepTime());
      System.out.println("Serves: " + rec.getServingCount());
      System.out.println("Ingredients:");
      for (int j = 0; j < ingredientCount; j++) {
        System.out.println(
          " -  " +
          rec.getIngredient(j).getIngredientName() +
          " - " +
          rec.getIngredient(j).getAmount()
        );
      }
    }

    System.out.println();
    System.out.println("Fourth test - 1 inactive, 1 missing");

    canMake = runtime.getRecipes(1);
    for (int i = 0; i < canMake.size(); i++) {
      Recipe rec = canMake.get(i);
      int ingredientCount = rec.getIngredientCount();
      System.out.println(rec.getName());
      System.out.println("Rating: " + rec.getAverageRating());
      System.out.println("Prep Time: " + rec.getPrepTime());
      System.out.println("Serves: " + rec.getServingCount());
      System.out.println("Ingredients:");
      for (int j = 0; j < ingredientCount; j++) {
        System.out.println(
          " -  " +
          rec.getIngredient(j).getIngredientName() +
          " - " +
          rec.getIngredient(j).getAmount()
        );
      }
    }
  }
}
