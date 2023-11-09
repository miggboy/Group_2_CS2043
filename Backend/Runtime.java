package Backend;

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
}
