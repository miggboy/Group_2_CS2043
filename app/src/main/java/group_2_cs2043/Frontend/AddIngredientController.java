package group_2_cs2043.Frontend;

import java.io.IOException;
import group_2_cs2043.Backend.Runtime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *This is a controller class for the Add Ingredient pop-up screen.
 *
 * @author Benjamin Martin & Miguel Daigle Gould
 * 
 */

public class AddIngredientController {

	@FXML
	private Button enterButton;

	@FXML
	private TextField ingredientField;
	
	@FXML
	private Label errorText;
	
	private Runtime runtime = new Runtime();

  /**
   * This ActionEvent handles creating a new Ingredient and storing it.
   * Checks for blank input and duplicates.
   * @throws IOException  
   */
  @FXML
  void onEnterClick(ActionEvent event) throws IOException {
	  String ingredientName = ingredientField.getText();
	  if(!(ingredientName.isBlank() || ingredientName.isEmpty())) {
		  if(runtime.addIngredient(ingredientName)) {
			  Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
			    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    Scene scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
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
