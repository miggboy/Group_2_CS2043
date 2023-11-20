package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeIngredientTest {

  RecipeIngredient recIng;

  @BeforeEach
  void before() {
    recIng = new RecipeIngredient("Name", "Amount", "Subs");
  }

  @Test
  void testGetAmount() {
    assertEquals(recIng.getAmount(), "Amount");
  }

  @Test
  void testGetIngredientName() {
    assertEquals(recIng.getIngredientName(), "Name");
  }

  @Test
  void testGetSubstitutions() {
    assertEquals(recIng.getSubstitutions(), "Subs");
  }
}
