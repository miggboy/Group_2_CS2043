package group_2_cs2043.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientTest {

  Ingredient ing;

  @BeforeEach
  void before() {
    ing = new Ingredient("Carrots", false);
  }

  @Test
  void testEquals() {
    assertTrue(ing.equals("Carrots"));
    assertFalse(ing.equals("Milk"));
  }

  @Test
  void testEquals2() {
    assertTrue(ing.equals(new Ingredient("Carrots", false)));
    assertFalse(ing.equals(new Ingredient("Milk", false)));
  }

  @Test
  void testGetName() {
    assertEquals(ing.getName(), "Carrots");
  }

  @Test
  void testIsAvailable() {
    assertFalse(ing.isAvailable());
  }

  @Test
  void testIsRemovable() {
    assertFalse(ing.isRemovable());
  }

  @Test
  void testSetAvailable() {
    assertFalse(ing.isAvailable());
    ing.setAvailable(true);
    assertTrue(ing.isAvailable());
  }

  @Test
  void testSetRemovable() {
    assertFalse(ing.isRemovable());
    ing.setRemovable(true);
    assertTrue(ing.isRemovable());
  }

  @Test
  void testToString() {
    assertEquals(ing.toString(), "Carrots");
  }
}
