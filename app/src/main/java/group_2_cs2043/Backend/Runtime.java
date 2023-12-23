package group_2_cs2043.Backend;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

/**
 * The `Runtime` class represents the runtime environment for the recipe
 * management system.
 * It encapsulates the lists of ingredients and recipes, providing methods to
 * retrieve individual ingredients and recipes.
 * The class handles the initialization of the backend by loading saved lists
 * during its construction.
 *
 * @author Max MacNeill
 * @version 1.0.0
 */
public class Runtime {

  /**
   * Deletes a directory recursively (Helper method)
   *
   * @param f The directory to delete
   */
  private static void deleteDirectory(File f) {
    File[] allContents = f.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    f.delete();
  }

  public static void createDefaultData() throws IOException {
    clearSavedData();
    Ingredient.writeDefaultList(0);
    Recipe.writeDefaultList(1);
  }

  /**
   * Deletes all saved data.
   */
  public static void clearSavedData() {
    String importPath = System.getProperty("user.home") + "/.RecipeBrowser";
    File f = new File(importPath);
    deleteDirectory(f);
  }

  private ArrayList<Ingredient> ingredientList;
  private ArrayList<Recipe> recipeList;

  /**
   * Constructs a new `Runtime` object, initializing the backend by loading saved
   * lists of ingredients and recipes.
   * If an exception occurs during initialization, the details are printed, and a
   * stack trace is provided.
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
   * Get the number of recipes
   *
   * @return the number of recipes
   */
  public int ingredientCount() {
    return ingredientList.size();
  }

  /**
   * Get the number of ingredients
   *
   * @return the number of recipes
   */
  public int recipeCount() {
    return recipeList.size();
  }

  /**
   * Retrieves an individual ingredient from the ingredient list by index.
   *
   * @param i The index of the ingredient to retrieve.
   * @return A copy of the `Ingredient` object at the specified index.
   */
  public Ingredient getIngredient(int i) {
    return new Ingredient(ingredientList.get(i));
  }

  /**
   * Checks whether an ingredient is available by index.
   *
   * @param i The index of the ingredient to retrieve.
   * @return a boolean representing whether the ingredient is available.
   */
  public boolean isAvailable(int i) {
    return ingredientList.get(i).isAvailable();
  }

  /**
   * Retrieves an individual recipe from the recipe list by index.
   *
   * @param i The index of the recipe to retrieve.
   * @return A copy of the `Recipe` object at the specified index.
   */
  public Recipe getRecipe(int i) {
    return new Recipe(recipeList.get(i));
  }

  /**
   * Saves the current runtime data to disc.
   *
   * @throws IOException if something breaks while writing the data.
   */
  private void saveRuntime() throws IOException {
    File f = new File(System.getProperty("user.home") + "/.RecipeBrowser");
    String[] entries = f.list();
    for (String s : entries) {
      File currentFile = new File(f.getPath(), s);
      currentFile.delete();
    }

    Ingredient.writeCurrentList(ingredientList);
    Recipe.writeCurrentList(recipeList);
  }

  /**
   * Adds a new ingredient to the runtime.
   *
   * @param in The ingredient to be added
   * @return true if the ingredient was sucessfully added, false if an ingredient
   *         with the same name already existed.
   * @throws IOException if the data fails to save to the drive.
   */
  public boolean addIngredient(String in) throws IOException {
    boolean success = true;

    for (int i = 0; i < ingredientList.size(); i++) {
      if (ingredientList.get(i).equals(in)) {
        success = false;
      }
    }

    if (success) {
      ingredientList.add(new Ingredient(in, true));
      saveRuntime();
    }

    return success;
  }

  /**
   * Adds a new recipe to the runtime.
   *
   * @param nameIn         the name of the recipe.
   * @param instructionsIn the instructions to prepare the recipe.
   * @param prepTimeIn     how long it takes to prepare the recipe,
   *                       including ingredient prep and cooking.
   * @param servingCountIn how many people can be served by one patch of this
   *                       recipe.
   * @param ingredients    takes 3 arrays of strings, of the same length, the
   *                       first including ingredient names, the second containing
   *                       the quantity of the ingredient at the same index, and
   *                       the third including substitutions for the ingredient
   *                       name at the same index.
   * @return true if the ingredient was sucessfully added, false if an ingredient
   *         with the same name already existed.
   * @throws IOException              if the data fails to save to the drive.
   * @throws IllegalArgumentException if the ingredients arrays are not correctly
   *                                  configured.
   */
  public boolean addRecipe(
    String nameIn,
    String instructionsIn,
    Duration prepTimeIn,
    int servingCountIn,
    String[] ingredients
  ) throws IOException {

    boolean success = true;
    for (int i = 0; i < recipeList.size(); i++) {
      if (recipeList.get(i).getName().equals(nameIn)) {
        success = false;
      }
    }

    Recipe toSave = new Recipe(
      nameIn,
      instructionsIn,
      prepTimeIn,
      servingCountIn,
      false
    );

    for (int i = 0; i < ingredients.length; i++) {
      toSave.addIngredient(
        ingredients[i]
      );
    }

    if (success) {
      recipeList.add(toSave);
      saveRuntime();
    }

    return success;
  }

  /**
   * Sets an ingredient within the runtime as available, if it exist, then saves
   * the runtime data to disk.
   *
   * @param name The name of the ingredient to be set as available.
   * @throws IOException if writing to disk fails.
   */
  public void setAvailable(String name) throws IOException {
    for (int i = 0; i < ingredientList.size(); i++) {
      if (ingredientList.get(i).equals(name)) {
        ingredientList.get(i).setAvailable(true);
      }
    }
    this.saveRuntime();
  }

  /**
   * Sets an ingredient within the runtime as unavailable, if it exist, then saves
   * the runtime data to disk.
   *
   * @param name The name of the ingredient to be set as unavailable.
   * @throws IOException if writing to disk fails.
   */
  public void setUnAvailable(String name) throws IOException {
    for (int i = 0; i < ingredientList.size(); i++) {
      if (ingredientList.get(i).equals(name)) {
        ingredientList.get(i).setAvailable(false);
      }
    }
    this.saveRuntime();
  }

  /**
   * Sets a recipe as a favorite by name.
   * @param name The name of the Recipe (case sensitive)
   * @param in if it should be a favorite or not.
   */
  public void setFavorite(String name, boolean in) throws IOException {
    for (int i = 0; i < recipeList.size(); i++) {
      if (recipeList.get(i).getName().equals(name)) {
        recipeList.get(i).setFavorite(in);
        saveRuntime();
      }
    }
  }

  /**
   * Returns all recipes with favorite=true
   *
   * @return the recipes
   */
  public ArrayList<Recipe> getFavoriteRecipes() {
    ArrayList<Recipe> ret = new ArrayList<Recipe>();

    for (int i = 0; i < recipeList.size(); i++) {
      if (recipeList.get(i).isFavorite()) {
        ret.add(recipeList.get(i));
      }
    }

    return ret;
  }

  /**
   * Returns an ArrayList of recipes with exactly the specified number of missing
   * ingredients (Using the existing array of available ingredients.)
   * Recipes with fewer missing ingredients will not be returned. For example, if
   * 1 missing ingredient is requested, recipes with all ingredients available
   * will not return.
   *
   * @param missing the number of ingredients missing to filter for.
   * @return an ArrayList of Recipes
   */
  public ArrayList<Recipe> getRecipes(int missing) {
    ArrayList<Recipe> ret = new ArrayList<Recipe>();

    for (int i = 0; i < recipeList.size(); i++) {
      Recipe buffer = recipeList.get(i);
      int count = buffer.getIngredientCount();
      int match = 0;
      for (int j = 0; j < count; j++) {
        String compare = buffer.getIngredient(j).getIngredientName();
        boolean found = false;
        for (int k = 0; k < ingredientList.size(); k++) {
          Ingredient test = ingredientList.get(k);
          if (test.equals(compare) && test.isAvailable()) {
            found = true;
          }
        }
        if (!found) {
          match += 1;
        }
      }
      if (match == missing) {
        ret.add(new Recipe(buffer));
      }
    }

    return ret;
  }

  /**
   * Takes a list of Recipes and returns that list with only recipes that match the requested time.
   *
   * @param list The list of recipes.
   * @param desTime The Desired Prep Time
   * @return newList: The arrayList of recipes that can be prepped in the cooking time range specified
   * 				  by the user.
   * @author Jaspreet S.Bedi
   */
  public ArrayList<Recipe> comPrepTime(
    ArrayList<Recipe> list,
    Duration desTime
  ) {
    ArrayList<Recipe> newList = new ArrayList<Recipe>();

    for (Recipe x : list) {
      if (x.getPrepTime().equals(desTime)) {
        newList.add(x);
      }
    }

    return newList;
  }

  /**
   * Takes a recipe and returns what ingredients are missing from that recipe.
   *
   * @param recIn the Recipe to be checked
   * @return The ingredients not marked as available.
   */
  public ArrayList<RecipeIngredient> getMissingIngredients(Recipe recIn) {
    ArrayList<RecipeIngredient> list = new ArrayList<RecipeIngredient>();
    for (int i = 0; i < recIn.getIngredientCount(); i++) {
      RecipeIngredient compare = recIn.getIngredient(i);
      for (int j = 0; j < ingredientList.size(); j++) {
        Ingredient compare2 = ingredientList.get(j);
        if (
          compare.getIngredientName().equals(compare2.getName()) &&
          !compare2.isAvailable()
        ) {
          list.add(new RecipeIngredient(compare));
        }
      }
    }
    return list;
  }

  /**
   * Takes a String representing an ingredient to check and remove from the ingredient list
   *
   * @param name the ingredient to be checked and removed
   */

  public void removeIngredient(String name) throws IOException {
    for (int i = 0; i < ingredientList.size(); i++) {
      if (ingredientList.get(i).getName().equals(name)) {
        ingredientList.remove(i);
        i--;
        this.saveRuntime();
      }
    }
  }

  /**
   * Takes a String representing a recipe to check and remove from the recipe list
   *
   * @param name the Recipe to be checked and removed
   */
  public void removeRecipe(String name) throws IOException {
    for (int i = 0; i < recipeList.size(); i++) {
      if (recipeList.get(i).getName().equals(name)) {
        recipeList.remove(i);
        i--;
        this.saveRuntime();
      }
    }
  }

  /**
   * Creates a shopping list for all missing ingredients from an ArrayList of Recipes.
   * This assumes that ingredients marked as available are available in large enough
   * quantities to satisfy all recipes that include them, user beware.
   *
   * @param in an Arraylist of Recipes to create a list for.
   * @return The Names and Quantities of missing Ingredients.
   *         Names may be duplicated if multiple recipes requre them.
   */
  public ArrayList<RecipeIngredient> makeShoppingList(ArrayList<Recipe> in) {
    ArrayList<RecipeIngredient> ret = new ArrayList<RecipeIngredient>();

    for (Recipe x : in) {
      for (int i = 0; i < x.getIngredientCount(); i++) {
        boolean add = true;
        RecipeIngredient y = x.getIngredient(i);

        for (int j = 0; j < ingredientList.size(); j++) {
          if (
            (y.getIngredientName().equals(ingredientList.get(j).getName())) &&
            ingredientList.get(j).isAvailable()
          ) {
            add = false;
          }
        }
        if (add) {
          ret.add(y);
        }
      }
    }

    return ret;
  }

  /**
   * Takes a list of Recipes and returns only those the user has favorited.
   * @param in The list of recipes to check
   * @return The list filtered down to only favorites.
   */
  public ArrayList<Recipe> filterFavorites(ArrayList<Recipe> in) {
    ArrayList<Recipe> ret = new ArrayList<Recipe>();

    for (int i = 0; i < in.size(); i++) {
      if (in.get(i).isFavorite()) {
        ret.add(in.get(i));
      }
    }

    return ret;
  }

  /**
   * Returns all Recipes containing a set ingredient.
   *
   * @param name The ingredient to check for
   * @return The list of Recipes
   */
  public ArrayList<Recipe> filterIngredient(String name) {
    ArrayList<Recipe> list = new ArrayList<Recipe>();

    for (Recipe rec : recipeList) {
      boolean hasIngredient = false;
      for (int i = 0; i < rec.getIngredientCount(); i++) {
        RecipeIngredient ing = rec.getIngredient(i);
        if (ing.getIngredientName().equals(name)) {
          hasIngredient = true;
        }
      }
      if (hasIngredient) {
        list.add(rec);
      }
    }

    return list;
  }

  /**
   * Returns all Recipes from an inputted list of Recipes containing a set ingredient.
   * @param name The ingredient to check for
   * @param in The list of Recipes to check from
   * @return the list of Recipes with the ingredient
   */
  public ArrayList<Recipe> filterIngredient(String name, ArrayList<Recipe> in) {
    ArrayList<Recipe> list = new ArrayList<Recipe>();

    for (Recipe rec : in) {
      boolean hasIngredient = false;
      for (int i = 0; i < rec.getIngredientCount(); i++) {
        RecipeIngredient ing = rec.getIngredient(i);
        if (ing.getIngredientName().equals(name)) {
          hasIngredient = true;
        }
      }
      if (hasIngredient) {
        list.add(rec);
      }
    }

    return list;
  }

  /**
   * Returns all the Recipes from a list that have a rating equal to or higher than
   * the inputted value.
   * @param in The filter Value
   * @param toFilter The list to be filted
   * @return The filtered List.
   */
  public ArrayList<Recipe> filterRating(double in, ArrayList<Recipe> toFilter) {
    ArrayList<Recipe> ret = new ArrayList<Recipe>();

    for (int i = 0; i < toFilter.size(); i++) {
      if (toFilter.get(i).getAverageRating() >= in) {
        ret.add(toFilter.get(i));
      }
    }

    return ret;
  }

  /**
   * Quickly returns all recipes including a certain ingredient and with a rating higher than 4 stars and optionally
   * Only which the user has Favorited.
   *
   * @param in The Ingredient to find recipes for
   * @param fav
   * @return a list of Recipes that meet the criteria
   */
  public ArrayList<Recipe> quickSearch(String in, boolean fav) {
    ArrayList<Recipe> ret = new ArrayList<Recipe>();

    ret = filterIngredient(in);
    ret = filterRating(4, ret);

    if (fav) {
      ret = filterFavorites(ret);
    }

    return ret;
  }
}
