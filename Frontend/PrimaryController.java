package Frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Backend.Ingredient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class PrimaryController implements Initializable{
	
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

}
