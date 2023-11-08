import Backend.Recipe;
import Backend.Runtime;

public class Example {

  public static void main(String[] args) {
    Runtime testing = new Runtime();
    Recipe rec = testing.getRecipes(0);
    int ingredientCount = rec.getIngredientCount();
    System.out.println(rec.getName());
    System.out.println("Rating: " + rec.getAverageRating());
    System.out.println("Prep Time: " + rec.getPrepTime());
    System.out.println("Serves: " + rec.getServingCount());
    System.out.println("Ingredients:");
    for (int i = 0; i < ingredientCount; i++) {
      System.out.println(
        " -  " +
        rec.getIngredient(i).getIngredientName() +
        " - " +
        rec.getIngredient(i).getAmount()
      );
    }
    System.out.println("Instructions:\n");
    System.out.println(rec.getInstructions());
  }
}
