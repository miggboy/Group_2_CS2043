import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Backend.Ingredient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PrimaryController implements Initializable{

    @FXML
    private Button addNewIngredient;
	
	@FXML
	private ListView<Ingredient> ingredientsListView;
	
	@FXML
	private ListView<Ingredient> selectedListView;

	private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
	private Ingredient currentIngredient;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			ingredientArray = Ingredient.loadSavedList();
		}
		catch(IOException e) {
			
		}
		ingredientsListView.getItems().addAll(ingredientArray);
		
		//Adds listener to Available Ingredients list, then adds selected ingredients to Selected list
		
		ingredientsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ingredient>() {

			@Override
			public void changed(ObservableValue<? extends Ingredient> arg0, Ingredient arg1, Ingredient arg2) {
				currentIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
				
				if(! selectedListView.getItems().contains(currentIngredient)) {
					selectedListView.getItems().add(currentIngredient);	
				}
			}
			
		});
	}

    /** 
     * This Action Event button loads a second scene onto the stage, prompting the user to enter a new ingredient.
     * @param event
     */
    @FXML
    void addIngredientAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addIngredient.fxml"));
            Parent root1 = loader.load();
         
            Stage stage1 = new Stage();
            Scene scene1 = new Scene(root1);
            stage1.setScene(scene1);
            stage1.show();
        } catch (IOException e) {
            System.out.println("Cannot load new window: " + e.getMessage());
        }
    }


}