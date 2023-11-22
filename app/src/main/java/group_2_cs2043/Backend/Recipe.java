package group_2_cs2043.Backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `Recipe` class represents a recipe, including its name, instructions,
 * preparation time, serving count, ingredients, and ratings.
 * This class is used to manage and retrieve information about recipes, such as
 * their details and ingredient list.
 *
 * This class serves as a component of a recipe management system, allowing
 * users to interact with and manipulate ingredient data.
 *
 * @author Max MacNeill
 * @version 1.0.0
 */
public class Recipe implements Serializable {

  /**
   * Loads the saved list of recipes from disk or calls the makeDefaultList method
   * with a specific state if the file is not present.
   * This method performs the following steps:
   * 1. Checks if the directory for saving the list exists. If not, it calls
   * makeDefaultList(0).
   * 2. Ensures that the directory is, in fact, a directory. If not, it throws an
   * error.
   * 3. Appends the filename "Recipes.bin" to the path and checks if the file
   * exists. If not, it calls makeDefaultList(1).
   * 4. Verifies that the path does not point to a directory. If it does, it
   * throws an error.
   * 5. Creates an object input stream and loads the file.
   * 6. Throws a ClassCastException if the loaded file does not contain a valid
   * ArrayList of recipes, then exits the JVM if any exception was thrown.
   * 7. Returns the ArrayList as initially requested if no errors were thrown.
   *
   * @return An ArrayList containing the loaded recipes.
   * @throws IOException if a file exists where the configuration folder should be
   *                     or a folder exists where the file should be,
   *                     configuration cannot be read or created.
   */
  public static ArrayList<Recipe> loadSavedList()
    throws IOException, IllegalArgumentException {
    // Directory path for saving the list
    String importPath = System.getProperty("user.dir") + "\\.RecipeBrowser";
    File f = new File(importPath);
    // Step 1: Verify if the directory exists, and handle accordingly
    if (!f.exists()) {
      Recipe.writeDefaultList(0);
    } else if (!f.isDirectory()) {
      throw new IOException(
        System.getProperty("user.dir") +
        "\\.RecipeBrowser" +
        " is a file, where it should be a folder. Execution cannot continue."
      );
    }

    // Append the filename to the path
    importPath += "\\Recipes.bin";
    f = new File(importPath);

    // Step 3: Verify if the file exists, and handle accordingly
    if (!f.exists()) {
      Recipe.writeDefaultList(1);
    } else if (f.isDirectory()) {
      throw new IOException(
        System.getProperty("user.dir") +
        "\\.RecipeBrowser\\Recipes.bin" +
        " is a folder, where it should be a file. Execution cannot continue."
      );
    }

    // Load the file and return the list of recipes
    ArrayList<Recipe> recipesList = new ArrayList<Recipe>(); // avoid compiler warnings about uninitialized value within
    // Try block which cannot actually be uninitialized
    try (
      ObjectInputStream objIn = new ObjectInputStream(
        new FileInputStream(importPath)
      )
    ) {
      Object obj = objIn.readObject();
      if (obj instanceof ArrayList<?>) {
        /*
         * I don't know why, I don't want to know why, I shouldn't have to wonder why,
         * but the warning about the unchecked cast will not go away unless I do this
         * local variable terribleness.
         */
        @SuppressWarnings("unchecked")
        ArrayList<Recipe> recipesListTemp = (ArrayList<Recipe>) obj;
        recipesList = recipesListTemp;
      } else {
        throw new ClassCastException(
          "Recipes.bin is not an ArrayList of Recipes"
        );
      }
    } catch (Exception e) {
      // Handle exceptions and exit on failure
      System.out.println(
        "Exception thrown reading serialized object: " + e.getMessage()
      );
      System.out.println(
        "Execution cannot continue without a valid default recipes list. Exiting."
      );
      System.exit(-1);
    }

    return recipesList;
  }

  /**
   * Utility method for creating and writing a default recipes list to a file.
   * This method serves various purposes depending on the provided 'state'
   * parameter:
   * - If 'state' is 0, it creates the necessary directory structure for saving
   * the list, then moves to state 1.
   * - If 'state' is 1, it generates a default recipes list, serializes it, and
   * writes it to the designated file.
   *
   * @param state An integer representing the desired action or initial state:
   *              - 0 for creating directories.
   *              - 1 for generating and writing a default recipes list.
   *
   * @throws IOException              if there are issues with file I/O
   *                                  operations.
   * @throws IllegalArgumentException if an unsupported 'state' value is provided.
   */
  public static void writeDefaultList(int state)
    throws IOException, IllegalArgumentException {
    if (state == 0) {
      new File(System.getProperty("user.dir") + "\\.RecipeBrowser").mkdir();
      state = 1;
    }
    if (state == 1) {
      ArrayList<Recipe> list = makeDefaultList();
      ObjectOutputStream objOut = new ObjectOutputStream(
        new FileOutputStream(
          System.getProperty("user.dir") + "\\.RecipeBrowser\\Recipes.bin"
        )
      );
      objOut.writeObject(list);
      objOut.close();
    }
    if (state != 1) throw new IllegalArgumentException(
      "A state was requested which this function does not implement."
    );
  }

  /**
   * Generates and returns a default list of recipes. This method does not make
   * any changes to the filesystem.
   * The generated list includes several common recipes with the removable flag
   * set to false.
   * Any new recipes needed for new recipes added to the backend should be added
   * here.
   *
   * @return An ArrayList of Ingredient objects representing the default recipes
   *         list.
   */
  public static ArrayList<Recipe> makeDefaultList() {
    // Create an ArrayList to hold the default recipes
    ArrayList<Recipe> defaultRecipes = new ArrayList<Recipe>();

    // Define and initialize individual recipes
    Recipe one = new Recipe(
      "Salmon with Brown Sugar Glaze",
      "Preheat the oven to 425\u00B0F. Position a rack in the center, line a sheet pan with foil, and set it aside. \n" +
      "\n" +
      "In a small bowl, stir the brown sugar, mustard, honey, salt, black pepper, and red pepper flakes until smooth and drizzly. \n" +
      "\n" +
      "If you like to eat the salmon skin, pat it very, very dry with a clean dish towel or paper towels. Skip this step if you prefer not to eat the skin. \n" +
      "\n" +
      "Place the filets, skin side-down and with space between each filet, on the prepared sheet pan. \n" +
      "\n" +
      "Spoon the glaze onto the filets, making sure it fully covers the tops. Use the back of the spoon to spread it out. \n" +
      "\n" +
      "Bake until the tops are golden brown and crispy in spots. The salmon will feel slightly firm when pressed with your fingers and it should flake easily with a fork, 12 to 14 minutes. If your filets are thin (less than an inch thick), start checking for doneness at the 10-minute mark. A digital thermometer inserted into the thickest part of the salmon should register between 125\u00B0F and 130\u00B0F\u2014this is considered cooked medium.\n" +
      "\n" +
      "Use a spatula to transfer the salmon onto plates and serve warm. \n" +
      "\n" +
      "Store leftovers in the fridge for up to 4 days. To reheat, place it on a foil-lined sheet pan and bake it slowly at 300\u00B0F until warmed through. This low and slow method will prevent the salmon from drying out too much.",
      25,
      4,
      true
    );
    one.addIngredient("Brown Sugar", "2 Tablespoons");
    one.addIngredient("Dijon Mustard", "1 Tablespoon");
    one.addIngredient("Honey", "1 Tablespoon");
    one.addIngredient("Kosher Salt", "1 Tablespoon");
    one.addIngredient("Black Pepper", "1/4 Teaspoon");
    one.addIngredient("Red Pepper Flakes", "1/4 Teaspoon");
    one.addIngredient("Salmon Filets", "4");
    one.addRating(5);
    one.addRating(5);
    one.addRating(5);

    // Add the recipes to the default list
    defaultRecipes.add(one);

    // Return the default recipes list
    return defaultRecipes;
  }

  /**
   * Writes the inputted list of Recipes to the save file.
   *
   * @param input the ArrayList to be saved.
   * @throws IOException if there are issues with file I/O operations.
   */
  public static void writeCurrentList(ArrayList<Recipe> input)
    throws IOException {
    String exportPath = System.getProperty("user.dir") + "\\.RecipeBrowser";
    File f = new File(exportPath);
    if (!f.exists()) {
      f.mkdir();
    } else if (!f.isDirectory()) {
      throw new IOException(
        System.getProperty("user.dir") +
        "\\.RecipeBrowser" +
        " is a file, where it should be a folder. Execution cannot continue."
      );
    }

    ObjectOutputStream objOut = new ObjectOutputStream(
      new FileOutputStream(exportPath + "\\Recipes.bin")
    );
    objOut.writeObject(input);
    objOut.close();
  }

  private String name;
  private String instructions;
  private int prepTime;
  private ArrayList<RecipeIngredient> ingredients;
  private int servingCount;
  private ArrayList<Integer> ratings;
  private boolean isDefault; // flag default recipies

  /**
   * Constructs a new Recipe object with the specified name, instructions,
   * preparation time, and serving count.
   *
   * @param nameIn         The name of the recipe.
   * @param instructionsIn The instructions for preparing the recipe.
   * @param prepTimeIn     The preparation time for the recipe.
   * @param servingCountIn The serving count or portion size for the recipe.
   * @param isDefault      Whether the recipe is one of the defaults, used to
   *                       signal the frontend not to allow deletion of it.
   */
  public Recipe(
    String nameIn,
    String instructionsIn,
    int prepTimeIn,
    int servingCountIn,
    boolean isDefault
  ) {
    name = nameIn;
    instructions = instructionsIn;
    prepTime = prepTimeIn;
    servingCount = servingCountIn;
    ingredients = new ArrayList<RecipeIngredient>();
    ratings = new ArrayList<Integer>();
    this.isDefault = isDefault;
  }

  /**
   * Creates a copy of the current recipe
   *
   * @param in The recipe to be copied
   * @return A copy of it.
   */
  public Recipe(Recipe in) {
    name = in.getName();
    instructions = in.getInstructions();
    prepTime = in.getPrepTime();
    ingredients = in.getIngredients();
    servingCount = in.getServingCount();
    ratings = in.getRatingArray();
    isDefault = in.isDefault;
  }

  public boolean isDefault() {
    return isDefault;
  }

  /**
   * Gets the name of the recipe.
   *
   * @return The name of the recipe as a String.
   */
  public String getName() {
    return new String(name);
  }

  /**
   * Gets the instructions for preparing the recipe.
   *
   * @return The instructions for preparing the recipe as a String.
   */
  public String getInstructions() {
    return new String(instructions);
  }

  /**
   * Gets the preparation time for the recipe.
   *
   * @return The preparation time for the recipe as a String.
   */
  public String getPrepTime() {
    return new String(prepTime);
  }

  /**
   * Gets the serving count or portion size for the recipe.
   *
   * @return The serving count or portion size as an integer.
   */
  public int getServingCount() {
    return servingCount;
  }

  /**
   * Adds a new rating to the recipe
   *
   * @param input the rating, as an Integer out of 5.
   * @throws IllegalArgumentException if a number not between 1 and 5 (inclusive)
   *                                  is entered.
   */
  public void addRating(int input) throws IllegalArgumentException {
    if (input < 1 || input > 5) throw new IllegalArgumentException(
      "Ratings must be from 1-5 (inclusive) but a " + input + " was provided"
    ); else ratings.add(input);
  }

  /**
   * Calculates and returns the average rating of the recipe based on user
   * ratings.
   *
   * @return The average rating as a double.
   */
  public double getAverageRating() {
    double total = 0;
    double count = 0;
    for (int i = 0; i < ratings.size(); i++) {
      total += ratings.get(i);
      count += 1;
    }
    return total / count;
  }

  /**
   * Gets the number of ingredients in the recipe.
   *
   * @return The number of ingredients as an integer.
   */
  public int getIngredientCount() {
    return ingredients.size();
  }

  /**
   * Retrieves a specific ingredient in the recipe by its index.
   *
   * @param index The index of the ingredient to retrieve.
   * @return A copy of the RecipeIngredient object at the specified index.
   * @throws IllegalArgumentException if the provided index is out of bounds.
   */
  public RecipeIngredient getIngredient(int index)
    throws IllegalArgumentException {
    if (index >= this.getIngredientCount()) throw new IllegalArgumentException(
      "The Index provided is higher than the list of ingredients allows."
    );
    return new RecipeIngredient(ingredients.get(index));
  }

  /**
   * Adds a new ingredient to the recipe.
   *
   * @param ingredient An `Ingredient` object to be included in the recipe.
   * @param quantity   The quantity or amount of the ingredient to be added to the
   *                   recipe.
   */
  public void addIngredient(String ingredient, String quantity) {
    addIngredient(new RecipeIngredient(ingredient, quantity));
  }

  /**
   * Adds a new ingredient to the recipe.
   *
   * @param ingredient    An `Ingredient` object to be included in the recipe.
   * @param quantity      The quantity or amount of the ingredient to be added to
   *                      the recipe.
   * @param substitutions A list of valid substitutions for this ingredient.
   */
  public void addIngredient(
    String ingredient,
    String quantity,
    String substitutions
  ) {
    addIngredient(new RecipeIngredient(ingredient, quantity, substitutions));
  }

  /**
   * Adds a new ingredient to the recipe.
   *
   * @param input A `RecipeIngredient` object to be included in the recipe.
   */
  public void addIngredient(RecipeIngredient input) {
    ingredients.add(input);
  }

  /**
   * Removes an ingredient from the recipe by name. It will remove all duplicate
   * names, if they exist.
   *
   * @param toRemove the name of the ingredient to remove
   * @return true if the ingredient existed and was removed, false otherwise.
   */
  public boolean removeIngredient(String toRemove) {
    boolean success = false;
    for (int i = 0; i < ingredients.size(); i++) {
      if (ingredients.get(i).getIngredientName().equals(toRemove)) {
        ingredients.remove(i);
        success = true;
        i--; // removed i, so check that index again.
      }
    }
    return success;
  }

  /**
   * A private class that allows the copy constructor to copy the ingredients
   * array without a for loop.
   *
   * @return the ingredients ArrayList.
   */
  private ArrayList<RecipeIngredient> getIngredients() {
    return ingredients;
  }

  /**
   * A private class that allows the copy constructor to copy the ratings array
   * without a for loop.
   *
   * @return the ratings ArrayList.
   */
  private ArrayList<Integer> getRatingArray() {
    return ratings;
  }
}
