package Backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class Ingredient {
    /**
     * Loads the saved list of ingredients from disk or calls the makeDefaultList method with a specific state if the file is not present.
     * This method performs the following steps:
     * 1. Checks if the directory for saving the list exists. If not, it calls makeDefaultList(0).
     * 2. Ensures that the path points to a directory. If not, it calls makeDefaultList(1).
     * 3. Appends the filename "Ingredients.bin" to the path and checks if the file exists. If not, it calls makeDefaultList(2).
     * 4. Verifies that the path does not point to a directory. If it does, it calls makeDefaultList(3).
     * 5. Creates an object input stream and loads the file.
     * 6. Throws a ClassCastException if the loaded file does not contain a valid ArrayList of Ingredients, then exits the JVM if any exception was thrown.
     * 7. Returns the ArrayList as initially requested if no errors were thrown.
     *
     * @return An ArrayList containing the loaded ingredients.
     * @throws IOException if a file exists where the configuration folder should be or a folder exists where the file should be, configuration cannot be read or created.
     */
    public static ArrayList<Ingredient> loadSavedList() throws IOException, IllegalArgumentException {
        // Directory path for saving the list
        String importPath = System.getProperty("user.dir") + "\\.RecipeBrowser";
        File f = new File(importPath);

        // Step 1: Verify if the directory exists, and handle accordingly
        if (!f.exists()) {
            Ingredient.writeDefaultList(0);
        } else if (!f.isDirectory()) {
            Ingredient.writeDefaultList(1);
        }

        // Append the filename to the path
        importPath += "\\Ingredients.bin";
        f = new File(importPath);

        // Step 3: Verify if the file exists, and handle accordingly
        if (!f.exists()) {
            Ingredient.writeDefaultList(3);
        } else if (f.isDirectory()) {
            Ingredient.writeDefaultList(2);
        }

        // Load the file and return the list of ingredients
        ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>(); // avoid compiler warnings about uninitialized value within Try block which cannot actually be uninitialized
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(importPath))) {
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
                throw new ClassCastException("defaultIngredients is not an ArrayList of Ingredients");
            }
        } catch (Exception e) {
            // Handle exceptions and exit on failure
            System.out.println("Exception thrown reading serialized object: " + e.getMessage());
            System.out.println("Execution cannot continue without a valid default ingredients list. Exiting.");
            System.exit(-1);
        }

        return ingredientsList;
    }

    /**
     * Utility method for creating and writing a default ingredients list to a file. Most of the checks are done in the loadSavedList() method,
     * so calling this method directly may be dangerous to the filesystem or JVM.
     * This method serves various purposes depending on the provided 'state' parameter:
     * - If 'state' is 0, it creates the necessary directory structure for saving the list, then moves to state 3.
     * - If 'state' is 1, it throws an IOException indicating that the intended location for the directory is already a file.
     * - If 'state' is 2, it throws an IOException indicating that the intended location for the file is already a directory.
     * - If 'state' is 3, it generates a default ingredients list, serializes it, and writes it to the designated file.
     * 
     * @param state An integer representing the desired action or initial state:
     *              - 0 for creating directories.
     *              - 1 for reporting a directory as a file.
     *              - 2 for reporting a file as a directory.
     *              - 3 for generating and writing a default ingredients list.
     * 
     * @throws IOException if there are issues with file I/O operations or the directory/file structure.
     * @throws IllegalArgumentException if an unsupported 'state' value is provided.
     */
    public static void writeDefaultList(int state) throws IOException, IllegalArgumentException{
        if (state == 0) {
            new File(System.getProperty("user.dir") + "\\.RecipeBrowser").mkdir();
            state = 3;
        }
        if (state == 1) {
            throw new IOException(System.getProperty("user.dir") + "\\.RecipeBrowser" + " is a file, where it should be a folder. Execution cannot continue.");
        }
        if (state == 2) {
            throw new IOException(System.getProperty("user.dir") + "\\.RecipeBrowser\\Ingredients.bin" + " is a folder, where it should be a file. Execution cannot continue.");
        }
        if (state == 3) {
            ArrayList<Ingredient> list = makeDefaultList();
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\.RecipeBrowser\\Ingredients.bin"));
            objOut.writeObject(list);
            objOut.close();
        }
        throw new IllegalArgumentException("A state was requested which this function does not implement.");
    }

    /**
     * Generates and returns a default list of ingredients. This method does not make any changes to the filesystem.
     * The generated list includes several common ingredients with the removable flag set to false. 
     * Any new ingredients needed for new recipies added to the backend should be added here.
     *
     * @return An ArrayList of Ingredient objects representing the default ingredients list.
     */
    public static ArrayList<Ingredient> makeDefaultList(){
        // Create an ArrayList to hold the default ingredients
        ArrayList<Ingredient> defaultIngredients = new ArrayList<Ingredient>();

        // Define and initialize individual ingredients
        Ingredient one = new Ingredient("carrots", false);
        Ingredient two = new Ingredient("beans", false);
        Ingredient three = new Ingredient("potatoes", false);
        Ingredient four = new Ingredient("corn", false);
        Ingredient five = new Ingredient("bacon", false);

        // Add the ingredients to the default list
        defaultIngredients.add(one);
        defaultIngredients.add(two);
        defaultIngredients.add(three);
        defaultIngredients.add(four);
        defaultIngredients.add(five);

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
        return name;
    }

    /**
     * Checks whether the ingredient is removable.
     * 
     * @return true if the ingredient is removable, false otherwise.
     */
    public boolean isRemovable(){
        return removable;
    }

    /**
     * Sets the removable status of the ingredient. It should be set if it is used in any recipie, and unset if the last recipie using it is removed.
     * 
     * @param isRemovable A boolean value indicating whether the ingredient is removable or not.
     */
    public void setRemovable(boolean isRemovable){
        removable = isRemovable;
    }
}