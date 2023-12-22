package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RuntimeTest {

  Runtime run = new Runtime();

  @BeforeAll
  static void initialize() {
    Runtime.clearSavedData();
  }

  @BeforeEach
  void before() throws IOException {
    Runtime.createDefaultData();
    run = new Runtime();
  }

  @Test
  void testAddIngredient() throws IOException {
    assertTrue(run.addIngredient("Ingredient"));
    assertFalse(run.addIngredient("Ingredient"));
    assertEquals(run.getIngredient(7).getName(), "Ingredient");
  }

  @Test
  void testAddRecipe() throws IOException {
    run.addIngredient("Ingredient");
    String[] arguments = new String[1];
    arguments[0] = "Ingredient";
    run.addRecipe("Name", "Instructions", Duration.ofMinutes(1), 0, arguments);
    run.setAvailable("Ingredient");
    Recipe rec = run.getRecipes(0).get(0);
    assertEquals(rec.getName(), "Name");
    assertEquals(rec.getInstructions(), "Instructions");
    assertEquals(rec.getPrepTime(), Duration.ofMinutes(1));
    assertEquals(rec.getServingCount(), 0);
  }

  @Test
  void testComPrepTime() throws IOException {
    run.addIngredient("Ingredient");
    String[] arguments = new String[1];
    arguments[0] = "Ingredient";
    run.addRecipe("Name", "Instructions", Duration.ofMinutes(1), 0, arguments);
    run.setAvailable("Ingredient");
    ArrayList<Recipe> list = run.getRecipes(0);
    ArrayList<Recipe> listTwo = run.comPrepTime(list, Duration.ofMinutes(1));
    ArrayList<Recipe> listThree = run.comPrepTime(list, Duration.ofHours(1));
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
    assertEquals(run.getRecipe(0).getName(), "Salmon with Brown Sugar Glaze");
    assertEquals(run.recipeCount(), 1);
  }

  @Test
  void testSetAvailable() throws IOException {
    assertFalse(run.isAvailable(2));
    run.setAvailable("Honey");
    assertTrue(run.isAvailable(2));
    run.setUnAvailable("Honey");
  }

  @Test
  void testSetUnAvailable() throws IOException {
    assertFalse(run.isAvailable(2));
    run.setAvailable("Honey");
    assertTrue(run.isAvailable(2));
    run.setUnAvailable("Honey");
    assertFalse(run.isAvailable(2));
  }

  @Test
  void testGetMissingIngredients() throws IOException {
    Recipe x = run.getRecipe(0);
    assertEquals(run.getMissingIngredients(x).size(), 7);
    run.setAvailable("Honey");
    assertEquals(run.getMissingIngredients(x).size(), 6);
    for (int i = 0; i < x.getIngredientCount(); i++) {
      run.setAvailable(x.getIngredient(i).getIngredientName());
    }
    assertEquals(run.getMissingIngredients(x).size(), 0);

    run.addIngredient("Ingredient");

    run.setUnAvailable("Honey");

    assertEquals(run.getMissingIngredients(x).size(), 1);
    assertEquals(
      run.getMissingIngredients(x).get(0).getIngredientName(),
      "Honey"
    );
  }

  @Test
  void testShoppingList() throws IOException {
    run.setAvailable("Brown Sugar");
    run.setAvailable("Dijon Mustard");
    run.setAvailable("Honey");
    run.setAvailable("Black Pepper");
    run.setAvailable("Red Pepper Flakes");
    run.setAvailable("Salmon Filets");

    ArrayList<Recipe> rec = run.getRecipes(1);

    ArrayList<RecipeIngredient> shoppingList = run.makeShoppingList(rec);

    assertEquals(shoppingList.get(0).getIngredientName(), "Kosher Salt");

    run.setAvailable("Kosher Salt");

    shoppingList = run.makeShoppingList(rec);

    assertEquals(shoppingList.size(), 0);
  }

  @Test
  void testFilterFavorites() throws IOException {
    ArrayList<Recipe> rec = run.getRecipes(7);
    assertEquals(rec.size(), 1);
    assertEquals(run.filterFavorites(rec).size(), 0);
    run.setFavorite("Salmon with Brown Sugar Glaze", true);
    rec = run.getRecipes(7);
    assertEquals(run.filterFavorites(rec).size(), 1);
  }

  @Test
  void testFilterIngredient() {
    ArrayList<Recipe> rec = run.getRecipes(7);
    assertEquals(rec.size(), 1);
    assertEquals(run.filterIngredient("Honey").size(), 1);
    assertEquals(run.filterIngredient("NotReal").size(), 0);
    assertEquals(run.filterIngredient("Honey", rec).size(), 1);
    assertEquals(run.filterIngredient("NotReal", rec).size(), 0);
  }

  @Test
  void testFilterRating() {
    ArrayList<Recipe> rec = run.getRecipes(7);
    assertEquals(rec.size(), 1);
    assertEquals(run.filterRating(5, rec).size(), 0);
    assertEquals(run.filterRating(4, rec).size(), 1);
  }

  @Test
  void testQuickSearch() throws IOException {
    ArrayList<Recipe> rec = run.getRecipes(7);
    assertEquals(rec.size(), 1);
    assertEquals(run.quickSearch("Honey", false).size(), 1);
    assertEquals(run.quickSearch("Honey", true).size(), 0);
    run.setFavorite("Salmon with Brown Sugar Glaze", true);
    assertEquals(run.quickSearch("Honey", true).size(), 1);
  }

  @AfterAll
  static void after() {
    Runtime.clearSavedData();
  }
}
