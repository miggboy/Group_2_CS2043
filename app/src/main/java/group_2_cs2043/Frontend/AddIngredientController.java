package group_2_cs2043.Frontend;

import java.io.IOException;
import java.util.ArrayList;
import group_2_cs2043.Backend.Ingredient; //TODO: This is unused?
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddIngredientController {

	@FXML
	private Button enterButton;

	@FXML
	private TextField ingredientField;
	
	@FXML
	private Label errorText;

  /**
   * This OnActionEvent stores the text from the specificed text field
   * Needs to update the list of ingredients on the previous scene.
   * @throws IOException  
   */
  @FXML
  void onEnterClick(ActionEvent event) throws IOException {
	  String ingredientName = ingredientField.getText();
	  
	  if(!(ingredientName.isBlank() || ingredientName.isEmpty())) {
		  Ingredient ingredient = new Ingredient(ingredientName, false);
		  ArrayList<Ingredient> ingredientArray = Ingredient.loadSavedList();
		  
		  //Testing shows .contains always returns false; whether the list contains the Ingredient or not
		  //Maybe .equals in Ingredient needs to be @Override'd?
		  //Manual iteration is in place for now
		  boolean containsIngredient = false;
		  for(int i = 0; i < ingredientArray.size(); i++) {
			  if(ingredientArray.get(i).getName().equals(ingredientName)) {
				  containsIngredient = true;
				  break;
			  }
		  }
		  
		  if(!containsIngredient) {
			  ingredientArray.add(ingredient);
			  Ingredient.writeCurrentList(ingredientArray);
			  Stage stage = (Stage) enterButton.getScene().getWindow();
		      stage.close();
		  }
		  else {
			  errorText.setTextFill(Color.RED);
			  errorText.setText("Error: List already contains ingredient");
		  }
	  }
	  else {
		  errorText.setTextFill(Color.RED);
		  errorText.setText("Error: Please enter an ingredient");		  
	  }
  }
}
