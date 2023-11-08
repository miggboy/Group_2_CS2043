package Backend;

import java.util.ArrayList;

public class Runtime {

  private ArrayList<Ingredient> ingredientList;
  private ArrayList<Recipe> recipeList;

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

  public Ingredient getIngredients(int i) {
    return new Ingredient(ingredientList.get(i));
  }

  public Recipe getRecipes(int i) {
    return new Recipe(recipeList.get(i));
  }
}
