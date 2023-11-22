package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeTest {

  Recipe rec;

  @BeforeEach
  void before() {
    rec = new Recipe("Name", "Instructions", Duration.ofMinutes(1), 4, false);
    rec.addIngredient("Ingredient", "Quantity", "Substitution");
  }

  @Test
  void testAddRating() {
    assertEquals(rec.getAverageRating(), (0.0 / 0.0));
    rec.addRating(4);
    assertEquals(rec.getAverageRating(), 4.0);
    rec.addRating(5);
    assertEquals(rec.getAverageRating(), 4.5);
  }

  @Test
  void testGetIngredient() {
    RecipeIngredient ing = rec.getIngredient(0);

    assertEquals(ing.getIngredientName(), "Ingredient");
    assertEquals(ing.getAmount(), "Quantity");
    assertEquals(ing.getSubstitutions(), "Substitution");
  }

  @Test
  void testGetIngredientCount() {
    assertEquals(rec.getIngredientCount(), 1);
  }

  @Test
  void testGetInstructions() {
    assertEquals(rec.getInstructions(), "Instructions");
  }

  @Test
  void testGetName() {
    assertEquals(rec.getName(), "Name");
  }

  @Test
  void testGetPrepTime() {
    assertEquals(rec.getPrepTime(), Duration.ofMinutes(1));
  }

  @Test
  void testGetServingCount() {
    assertEquals(rec.getServingCount(), 4);
  }

  @Test
  void testIsDefault() {
    assertFalse(rec.isDefault());
  }

  @Test
  void testRemoveIngredient() {
    rec.removeIngredient("Ingredient");
    assertEquals(rec.getIngredientCount(), 0);
  }

  @Test
  void testFavorite() {
    assertFalse(rec.isFavorite());
    rec.setFavorite(true);
    assertTrue(rec.isFavorite());
    rec.setFavorite(false);
    assertFalse(rec.isFavorite());
  }
}
