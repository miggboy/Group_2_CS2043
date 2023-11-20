package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RuntimeTest {

  Runtime run = new Runtime();

  @BeforeAll
  static void initialize() {
    Runtime.clearSavedData();
  }

  @Test
  void testAddIngredient() throws IOException {
    assertTrue(run.addIngredient("Ingredient"));
    assertEquals(run.getIngredient(7).getName(), "Ingredient");
  }

  @Test
  void testAddRecipe() throws IOException {
    run.addIngredient("Ingredient");
    String[][] arguments = new String[3][1];
    arguments[0][0] = "Ingredient";
    arguments[1][0] = "Quantity";
    arguments[2][0] = "Substitution";
    run.addRecipe("Name", "Instructions", "Time", 0, arguments);
    run.setAvailable("Ingredient");
    Recipe rec = run.getRecipes(0).get(0);
    assertEquals(rec.getName(), "Name");
    assertEquals(rec.getInstructions(), "Instructions");
    assertEquals(rec.getPrepTime(), "Time");
    assertEquals(rec.getServingCount(), 0);
  }

  @Test
  void testComPrepTime() throws IOException {
    run.addIngredient("Ingredient");
    String[][] arguments = new String[3][1];
    arguments[0][0] = "Ingredient";
    arguments[1][0] = "Quantity";
    arguments[2][0] = "Substitution";
    run.addRecipe("Name", "Instructions", "3 Minutes", 0, arguments);
    run.setAvailable("Ingredient");
    ArrayList<Recipe> list = run.getRecipes(0);
    ArrayList<Recipe> listTwo = run.comPrepTime(list, "3 Minutes");
    ArrayList<Recipe> listThree = run.comPrepTime(list, "3 Hours");
    assertEquals(listTwo.size(), 1);
    assertEquals(listThree.size(), 0);
  }

  @Test
  void testGetIngredient() {
    assertEquals(run.getIngredient(2).getName(), "Honey");
  }

  @Test
  void testGetRecipe() {
    assertEquals(run.getRecipe(0).getName(), "Salmon with Brown Sugar Glaze");
  }

  @Test
  void testGetRecipes() {
    Recipe rec = run.getRecipes(7).get(0);
    assertEquals(rec.getName(), "Salmon with Brown Sugar Glaze");
  }

  @Test
  void testIngredientCount() throws IOException {
    run.addIngredient("Ingredient");
    assertEquals(run.ingredientCount(), 8);
  }

  @Test
  void testIsAvailable() throws IOException {
    run.addIngredient("Ingredient");
    run.setAvailable("Ingredient");
    assertFalse(run.isAvailable(2));
    assertTrue(run.isAvailable(7));
  }

  @Test
  void testRecipeCount() {
    assertEquals(run.recipeCount(), 2);
  }

  @Test
  void testSetAvailable() throws IOException {
    assertFalse(run.isAvailable(2));
    run.setAvailable("Honey");
    assertTrue(run.isAvailable(2));
  }

  @Test
  void testSetUnAvailable() throws IOException {
    run.setUnAvailable("Honey");
    assertFalse(run.isAvailable(2));
  }

  @AfterAll
  static void after() {
    Runtime.clearSavedData();
  }
}
