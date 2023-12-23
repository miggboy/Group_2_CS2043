package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeIngredientTest {

  RecipeIngredient recIng;

  @BeforeEach
  void before() {
    recIng = new RecipeIngredient("Name");
  }

  @Test
  void testGetIngredientName() {
    assertEquals(recIng.getIngredientName(), "Name");
  }
}
