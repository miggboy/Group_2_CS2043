package group_2_cs2043.Frontend;

import group_2_cs2043.Backend.Ingredient; //TODO: This is unused?
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddIngredientController {

  @FXML
  private Button enterNewIngredient;

  @FXML
  private TextField newIngredientToAdd;

  /**
   * This OnActionEvent stores the text from the specificed text field
   * Needs to update the list of ingredients on the previous scene.
   */
  @FXML
  void enterNewIngredientToAdd(ActionEvent event) {
    String newIngredientName = newIngredientToAdd.getText(); //TODO: This is unused?
  }
}
