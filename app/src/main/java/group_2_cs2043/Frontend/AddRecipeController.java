package group_2_cs2043.Frontend;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Ingredient;
import group_2_cs2043.Backend.Recipe;
import group_2_cs2043.Backend.Runtime;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddRecipeController implements Initializable{
	private Runtime runtime = new Runtime();
	  
	//Variables for displaying and selecting Ingredients
	private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
	private ObservableList<Recipe> recipeList;

	@FXML
    private ChoiceBox<String> dropDownMenu;
    @FXML
    private TextField instructionField;
    @FXML
    private TextField prepTimeField;
    @FXML
    private TextField recipeNameField;
    @FXML
    private TextField servingSizeField;
    @FXML
    private ListView<Ingredient> ingredientsListView;
    private String[][] holder = {{"wow","1 cup",""}};

    @FXML
    void createButtonClick(ActionEvent event) throws NumberFormatException, IOException {
    	runtime.addRecipe(recipeNameField.getText(), 
    					  instructionField.getText(), 
    					  Duration.ofMinutes(Integer.parseInt(prepTimeField.getText())), 
    					  Integer.parseInt(servingSizeField.getText()), 
    					  holder);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Populate the ChoiceBox with ingredients
		ingredientArray = getSavedIngredientsArray();
		for(int i = 0; i < ingredientArray.size(); i++) {
	    	dropDownMenu.getItems().add(ingredientArray.get(i).getName());
	    }
		dropDownMenu.setOnAction(this::getIngredient);
		ingredientsListView.getItems().add(new Ingredient("Test",true));
		//
		
		ArrayList<Recipe> tempArray = new ArrayList<Recipe>();
		
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
		ingredientsListView.getItems().add(new Ingredient(myIngredient,true));
		/**
		ingredientsListView.getSelectionModel().selectedItemProperty().addListener((InvalidationListener) new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				
			}
		
		
		});
		*/
	}
    
}
