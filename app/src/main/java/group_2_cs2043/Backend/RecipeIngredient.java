package app.src.main.java.group_2_cs2043.Backend;

import java.io.Serializable;

/**
 * The `RecipeIngredient` class represents an ingredient within a recipe, including its name, amount, and potential substitutions.
 * Instances of this class are intended to be used as components in recipe representations.
 *
 * @author Max MacNeill
 * @version 1.0.0
 */
public class RecipeIngredient implements Serializable {
  private String ingredient;
  private String amount;
  private String substitutions;

  /**
   * Constructs a new `RecipeIngredient` object with the specified ingredient name and amount.
   *
   * @param ingredientName The name of the ingredient.
   * @param amountIn The quantity or amount of the ingredient to be used in the recipe.
   */
  public RecipeIngredient(String ingredientName, String amountIn) {
    ingredient = ingredientName;
    amount = amountIn;
    substitutions = "";
  }

  /**
   * Constructs a new `RecipeIngredient` object with the specified ingredient name, amount, and substitutions.
   *
   * @param ingredientName The name of the ingredient.
   * @param amountIn The quantity or amount of the ingredient to be used in the recipe.
   * @param substitutionsIn Any potential substitutions for the ingredient.
   */
  public RecipeIngredient(
    String ingredientName,
    String amountIn,
    String substitutionsIn
  ) {
    ingredient = ingredientName;
    amount = amountIn;
    substitutions = substitutionsIn;
  }

  /**
   * Constructs a new `RecipeIngredient` object by copying the properties of another `RecipeIngredient` object.
   *
   * @param copy The `RecipeIngredient` object to be copied.
   */
  public RecipeIngredient(RecipeIngredient copy) {
    ingredient = copy.getIngredientName();
    amount = copy.getAmount();
    substitutions = copy.getSubstitutions();
  }

  /**
   * Retrieves the name of the ingredient.
   *
   * @return The name of the ingredient as a String.
   */
  public String getIngredientName() {
    return new String(ingredient);
  }

  /**
   * Retrieves the amount of the ingredient.
   *
   * @return The amount of the ingredient as a String.
   */
  public String getAmount() {
    return new String(amount);
  }

  /**
   * Retrieves any potential substitutions for the ingredient.
   *
   * @return The substitutions for the ingredient as a String.
   */
  public String getSubstitutions() {
    return new String(substitutions);
  }
}
