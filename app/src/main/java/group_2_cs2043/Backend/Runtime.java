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
    Recipe.writeDefaultList(0);
  }

  /**
   * Deletes all saved data.
   */
  public static void clearSavedData() {
    String importPath = System.getProperty("user.dir") + "\\.RecipeBrowser";
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
    File f = new File(System.getProperty("user.dir") + "\\.RecipeBrowser");
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
    String[][] ingredients
  ) throws IOException {
    boolean arrayValid = true;
    if (ingredients.length != 3) {
      arrayValid = false;
    }
    if (
      ingredients[0].length != ingredients[1].length ||
      ingredients[1].length != ingredients[2].length
    ) {
      arrayValid = false;
    }

    if (!arrayValid) {
      throw new IllegalArgumentException("The ingredients array is invalid.");
    }

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

    for (int i = 0; i < ingredients[0].length; i++) {
      toSave.addIngredient(
        ingredients[0][i],
        ingredients[1][i],
        ingredients[2][i]
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
            System.out.println(
              "Found " + compare + " Which is available: " + test.isAvailable()
            );
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
  public ArrayList<Ingredient> getMissingIngredients(Recipe recIn) {
    ArrayList<Ingredient> list = new ArrayList<Ingredient>();
    for (int i = 0; i < recIn.getIngredientCount(); i++) {
      RecipeIngredient compare = recIn.getIngredient(i);
      for (int j = 0; j < ingredientList.size(); j++) {
        Ingredient compare2 = ingredientList.get(j);
        if (
          compare.getIngredientName().equals(compare2.getName()) &&
          !compare2.isAvailable()
        ) {
          list.add(new Ingredient(compare2));
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
  
  public void removeIngredient(String name) {
	    for (int i = 0; i < ingredientList.size(); i++) {
	      if (ingredientList.get(i).getIngredientName().equals(name)) {
	        ingredientList.remove(i);
	        i--; 
	      }
	    }
  }

  /**
   * Takes a String representing a recipe to check and remove from the recipe list
   *
   * @param name the Recipe to be checked and removed
   */
  public void removeRecipe(String name) {
	    
	    for (int i = 0; i < recipeList.size(); i++) {
	      if (recipeList.get(i).getName().equals(name)) {
	        recipeList.remove(i);
	        i--; 
	      }
	    }
  }