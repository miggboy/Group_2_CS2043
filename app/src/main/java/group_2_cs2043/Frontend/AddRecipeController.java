package group_2_cs2043.Frontend;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Ingredient;
import group_2_cs2043.Backend.Runtime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class AddRecipeController implements Initializable{
	private Runtime runtime = new Runtime();
	  
	//Variables for displaying and selecting Ingredients
	private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();

	@FXML
    private ChoiceBox<String> dropDownMenu;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ingredientArray = getSavedIngredientsArray();
		for(int i = 0; i < ingredientArray.size(); i++) {
	    	//if(ingredientArray.get(i).isAvailable())
	    	dropDownMenu.getItems().add(ingredientArray.get(i).getName());
	    }
		
	    /*
	    //Load all available ingredients, derived from existing ingredients
	    for(int i = 0; i < ingredientArray.size(); i++) {
	    	if(ingredientArray.get(i).isAvailable())
	    		selectedArray.add(ingredientArray.get(i));
	    }
	    
	    ingredientList = FXCollections.observableArrayList(ingredientArray); 	//Instantiate ingredientList with existing ingredients
	    selectedList = FXCollections.observableArrayList(selectedArray); 		//Instantiate selectedList with available ingredients
		*/
	}
	/**
	 * Helper method for getting saved Ingredients list from drive 
	 */
	public ArrayList<Ingredient> getSavedIngredientsArray() {
		  ArrayList<Ingredient> arr = new ArrayList<Ingredient>();
		  for(int i = 0; i < runtime.ingredientCount(); i++) {
			  arr.add(runtime.getIngredient(i));
		  }
		  return arr;
	 }
	
	public void getIngredient(ActionEvent event) {
		String myIngredient = dropDownMenu.getValue();
		
	}
    
}
