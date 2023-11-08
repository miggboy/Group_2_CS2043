package Backend;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {

  private String ingredient;
  private String amount;
  private String substitutions;

  public RecipeIngredient(String ingredientName, String amountIn) {
    ingredient = ingredientName;
    amount = amountIn;
    substitutions = null;
  }

  public RecipeIngredient(
    String ingredientName,
    String amountIn,
    String substitutionsIn
  ) {
    ingredient = ingredientName;
    amount = amountIn;
    substitutions = substitutionsIn;
  }

  public RecipeIngredient(RecipeIngredient copy) {
    ingredient = copy.getIngredientName();
    amount = copy.getAmount();
    substitutions = copy.getSubstitutions();
  }

  public String getIngredientName() {
    return new String(ingredient);
  }

  public String getAmount() {
    return new String(amount);
  }

  public String getSubstitutions() {
    return new String(substitutions);
  }
}
