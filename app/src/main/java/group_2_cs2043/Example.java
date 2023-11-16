package app.src.main.java.group_2_cs2043;

import app.src.main.java.group_2_cs2043.Backend.Ingredient;
import app.src.main.java.group_2_cs2043.Backend.Recipe;
import app.src.main.java.group_2_cs2043.Backend.Runtime;
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
    //The above should not be included in the final frontend and is for debugging only.

    Runtime testing = new Runtime();
    System.out.println("Testing Recipe Processing:\n");
    Recipe rec = testing.getRecipe(0);
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
    System.out.println();
    System.out.println("Testing Ingredient Processing:\n");
    Ingredient ing = testing.getIngredient(2);
    System.out.println("Name: " + ing.getName());
    System.out.println("Is Available: " + ing.isAvailable());
    System.out.println("Is Removable: " + ing.isRemovable());
  }
}
