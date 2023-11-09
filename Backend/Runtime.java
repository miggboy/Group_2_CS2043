package Backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The `Runtime` class represents the runtime environment for the recipe management system.
 * It encapsulates the lists of ingredients and recipes, providing methods to retrieve individual ingredients and recipes.
 * The class handles the initialization of the backend by loading saved lists during its construction.
 *
 * @author Max MacNeill
 * @version 1.0.0
 */
public class Runtime {

  private ArrayList<Ingredient> ingredientList;
  private ArrayList<Recipe> recipeList;

  /**
   * Constructs a new `Runtime` object, initializing the backend by loading saved lists of ingredients and recipes.
   * If an exception occurs during initialization, the details are printed, and a stack trace is provided.
   */
  public Runtime() {
    try {
      ingredientList = Ingredient.loadSavedList();
      recipeList = Recipe.loadSavedList();
    } catch (Exception e) {
      System.out.println(e.getClass());
      System.out.println(
        "The backend failed to initialize due to the following error: \n\n" +
        e.getMessage() +
        "\n\nA stack trace will follow."
      );
      e.printStackTrace();
    }
  }

  /**
   * Retrieves an individual ingredient from the ingredient list by index.
   *
   * @param i The index of the ingredient to retrieve.
   * @return A copy of the `Ingredient` object at the specified index.
   */
  public Ingredient getIngredients(int i) {
    return new Ingredient(ingredientList.get(i));
  }

  /**
   * Retrieves an individual recipe from the recipe list by index.
   *
   * @param i The index of the recipe to retrieve.
   * @return A copy of the `Recipe` object at the specified index.
   */
  public Recipe getRecipes(int i) {
    return new Recipe(recipeList.get(i));
  }

  /**
   * Saves the current runtime data to disc.
   *
   * @throws IOException if something breaks while writing the data.
   */
  private void saveRuntime() throws IOException {
    Ingredient.writeCurrentList(ingredientList);
    Recipe.writeCurrentList(recipeList);
  }

  /**
   * Adds a new ingredient to the runtime.
   *
   * @param in The ingredient to be added
   * @return true if the ingredient was sucessfully added, false if an ingredient with the same name already existed.
   * @throws IOException if the data fails to save to the drive.
   */
  public boolean addIngredient(Ingredient in) throws IOException {
    boolean success = true;

    for (int i = 0; i < ingredientList.size(); i++) {
      if (ingredientList.get(i).equals(in)) {
        success = false;
      }
    }

    if (success) {
      ingredientList.add(in);
    }

    File f = new File(System.getProperty("user.dir") + "\\.RecipeBrowser");
    String[] entries = f.list();
    for (String s : entries) {
      File currentFile = new File(f.getPath(), s);
      currentFile.delete();
    }

    saveRuntime();

    return success;
  }
}
