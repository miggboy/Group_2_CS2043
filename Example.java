import Backend.Ingredient;
import Backend.Recipe;
import Backend.Runtime;
import java.io.File;

public class Example {

  public static void main(String[] args) {
    //Remove the existing configuration for debugging purposes.
    File f = new File(System.getProperty("user.dir") + "\\.RecipeBrowser");
    String[] entries = f.list();
    for (String s : entries) {
      File currentFile = new File(f.getPath(), s);
      currentFile.delete();
    }
    f.delete();

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
