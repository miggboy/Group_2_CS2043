package app.src.main.java.group_2_cs2043.Backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The `Ingredient` class represents an ingredient in a recipe. It provides methods for managing the ingredient's properties,
 * such as name, availability, and removability. Additionally, the class offers functionality to load and create default
 * lists of ingredients.
 *
 * This class serves as a component of a recipe management system, allowing users to interact with and manipulate ingredient data.
 * @author Max MacNeill
 * @version 1.0.0
 */
public class Ingredient implements Serializable {

  /**
   * Loads the saved list of ingredients from disk or calls the makeDefaultList method with a specific state if the file is not present.
   * This method performs the following steps:
   * 1. Checks if the directory for saving the list exists. If not, it calls makeDefaultList(0).
   * 2. Ensures that the directory is, in fact, a directory. If not, it throws an error.
   * 3. Appends the filename "Ingredients.bin" to the path and checks if the file exists. If not, it calls makeDefaultList(1).
   * 4. Verifies that the path does not point to a directory. If it does, it throws an error.
   * 5. Creates an object input stream and loads the file.
   * 6. Throws a ClassCastException if the loaded file does not contain a valid ArrayList of Ingredients, then exits the JVM if any exception was thrown.
   * 7. Returns the ArrayList as initially requested if no errors were thrown.
   *
   * @return An ArrayList containing the loaded ingredients.
   * @throws IOException if a file exists where the configuration folder should be or a folder exists where the file should be, configuration cannot be read or created.
   */
  public static ArrayList<Ingredient> loadSavedList()
    throws IOException, IllegalArgumentException {
    // Directory path for saving the list
    String importPath = System.getProperty("user.dir") + "\\.RecipeBrowser";
    File f = new File(importPath);
    // Step 1: Verify if the directory exists, and handle accordingly
    if (!f.exists()) {
      Ingredient.writeDefaultList(0);
    } else if (!f.isDirectory()) {
      throw new IOException(
        System.getProperty("user.dir") +
        "\\.RecipeBrowser" +
        " is a file, where it should be a folder. Execution cannot continue."
      );
    }

    // Append the filename to the path
    importPath += "\\Ingredients.bin";
    f = new File(importPath);

    // Step 3: Verify if the file exists, and handle accordingly
    if (!f.exists()) {
      Ingredient.writeDefaultList(1);
    } else if (f.isDirectory()) {
      throw new IOException(
        System.getProperty("user.dir") +
        "\\.RecipeBrowser\\Ingredients.bin" +
        " is a folder, where it should be a file. Execution cannot continue."
      );
    }

    // Load the file and return the list of ingredients
    ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>(); // avoid compiler warnings about uninitialized value within Try block which cannot actually be uninitialized
    try (
      ObjectInputStream objIn = new ObjectInputStream(
        new FileInputStream(importPath)
      )
    ) {
      Object obj = objIn.readObject();
      if (obj instanceof ArrayList<?>) {
        /* I don't know why, I don't want to know why, I shouldn't have to wonder why,
         * but the warning about the unchecked cast will not go away unless I do this
         * local variable terribleness.
         */
        @SuppressWarnings("unchecked")
        ArrayList<Ingredient> ingredientsListTemp = (ArrayList<Ingredient>) obj;
        ingredientsList = ingredientsListTemp;
      } else {
        throw new ClassCastException(
          "Ingredients.bin is not an ArrayList of Ingredients"
        );
      }
    } catch (Exception e) {
      // Handle exceptions and exit on failure
      System.out.println(
        "Exception thrown reading serialized object: " + e.getMessage()
      );
      System.out.println(
        "Execution cannot continue without a valid default ingredients list. Exiting."
      );
      System.exit(-1);
    }

    return ingredientsList;
  }

  /**
   * Utility method for creating and writing a default ingredients list to a file.
   * This method serves various purposes depending on the provided 'state' parameter:
   * - If 'state' is 0, it creates the necessary directory structure for saving the list, then moves to state 1.
   * - If 'state' is 1, it generates a default ingredients list, serializes it, and writes it to the designated file.
   *
   * @param state An integer representing the desired action or initial state:
   *              - 0 for creating directories.
   *              - 1 for generating and writing a default ingredients list.
   *
   * @throws IOException if there are issues with file I/O operations.
   * @throws IllegalArgumentException if an unsupported 'state' value is provided.
   */
  public static void writeDefaultList(int state)
    throws IOException, IllegalArgumentException {
    if (state == 0) {
      new File(System.getProperty("user.dir") + "\\.RecipeBrowser").mkdir();
      state = 1;
    }
    if (state == 1) {
      ArrayList<Ingredient> list = makeDefaultList();
      ObjectOutputStream objOut = new ObjectOutputStream(
        new FileOutputStream(
          System.getProperty("user.dir") + "\\.RecipeBrowser\\Ingredients.bin"
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
   * Writes the inputted list of Ingredients to the save file.
   *
   * @param input the ArrayList to be saved.
   * @throws IOException if there are issues with file I/O operations.
   */
  public static void writeCurrentList(ArrayList<Ingredient> input)
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
      new FileOutputStream(exportPath + "\\Ingredients.bin")
    );
    objOut.writeObject(input);
    objOut.close();
  }

  /**
   * Generates and returns a default list of ingredients. This method does not make any changes to the filesystem.
   * The generated list includes several common ingredients with the removable flag set to false.
   * Any new ingredients needed for new recipies added to the backend should be added here.
   *
   * @return An ArrayList of Ingredient objects representing the default ingredients list.
   */
  public static ArrayList<Ingredient> makeDefaultList() {
    // Create an ArrayList to hold the default ingredients
    ArrayList<Ingredient> defaultIngredients = new ArrayList<Ingredient>();

    // Define and initialize individual ingredients
    Ingredient one = new Ingredient("Brown Sugar", false);
    Ingredient two = new Ingredient("Dijon Mustard", false);
    Ingredient three = new Ingredient("Honey", false);
    Ingredient four = new Ingredient("Kosher Salt", false);
    Ingredient five = new Ingredient("Black Pepper", false);
    Ingredient six = new Ingredient("Red Pepper Flakes", false);
    Ingredient seven = new Ingredient("Salmon Filets", false);

    // Add the ingredients to the default list
    defaultIngredients.add(one);
    defaultIngredients.add(two);
    defaultIngredients.add(three);
    defaultIngredients.add(four);
    defaultIngredients.add(five);
    defaultIngredients.add(six);
    defaultIngredients.add(seven);

    // Return the default ingredients list
    return defaultIngredients;
  }

  private String name;
  private boolean available;
  private boolean removable;

  /**
   * Constructs a new Ingredient object with the specified name and removable status.
   *
   * @param nameIn The name of the ingredient.
   * @param isRemovable A boolean indicating whether the ingredient is removable.
   */
  public Ingredient(String nameIn, boolean isRemovable) {
    name = nameIn;
    available = false;
    removable = isRemovable;
  }

  public Ingredient(Ingredient copy) {
    name = copy.getName();
    available = copy.isAvailable();
    removable = isRemovable();
  }

  /**
   * Sets the availability status of the ingredient.
   *
   * @param in A boolean value indicating whether the ingredient is available or not.
   */
  public void setAvailable(boolean in) {
    available = in;
  }

  /**
   * Checks whether the ingredient is available.
   *
   * @return true if the ingredient is available, false otherwise.
   */
  public boolean isAvailable() {
    return available;
  }

  /**
   * Gets the name of the ingredient.
   *
   * @return The name of the ingredient as a String.
   */
  public String getName() {
    return new String(name);
  }

  /**
   * Checks whether the ingredient is removable.
   *
   * @return true if the ingredient is removable, false otherwise.
   */
  public boolean isRemovable() {
    return removable;
  }

  /**
   * Sets the removable status of the ingredient. It should be set if it is used in any recipie, and unset if the last recipie using it is removed.
   *
   * @param isRemovable A boolean value indicating whether the ingredient is removable or not.
   */
  public void setRemovable(boolean isRemovable) {
    removable = isRemovable;
  }

  /**
   * Checks another ingredient to see if they have the same name.
   *
   * @param compare the Ingredient to be compared.
   * @return if they have the same name.
   */
  public boolean equals(Ingredient compare) {
    if (
      this.getName().equals(compare.getName())
    ) return true; else return false;
  }

  /**
   * Checks a String to see if it matches the ingredient's name.
   *
   * @param compare the String to be compared.
   * @return if they match.
   */
  public boolean equals(String compare) {
    if (this.getName().equals(compare)) return true; else return false;
  }

  /**
   * Returns the ingredient's name.
   *
   * @return the name.
   */
  public String toString() {
    return new String(name);
  }
}
