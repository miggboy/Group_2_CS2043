package group_2_cs2043.Frontend;

import java.awt.TextArea;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRecipeController implements Initializable{
	private Runtime runtime = new Runtime();
	  
	//Variable for displaying and selecting Ingredients
	private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();

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
    private ArrayList<Ingredient> newRecipeIngredients = new ArrayList<Ingredient>();
    private String[][] ingredients;
    /*
     * Creates a new recipe from the specified fields and adds it to runtime, then changes scene back to primary
     */
    @FXML
    void createButtonClick(ActionEvent event) throws NumberFormatException, IOException {
    	/*
    	 * Initialize ingredients with ingredients that have been added
    	 */
    	ingredients = new String[3][newRecipeIngredients.size()];
    	for (int i = 0; i < ingredients.length; i++) {
    	    for (int j = 0; j < ingredients[i].length; j++) {
    	    	ingredients[i][j] = "";
    	    }
    	}
    	/*
    	 * Populate the ingredients with appropriate names
    	 */
    	for(int i = 0; i < newRecipeIngredients.size();i++) {
    		ingredients[0][i] = newRecipeIngredients.get(i).getName();
    	}
    	/*
    	 * Create the new recipe with specified data
    	 */
    	runtime.addRecipe(recipeNameField.getText(), 
    			instructionField.getText(), 
    					  Duration.ofMinutes(Integer.parseInt(prepTimeField.getText())), 
    					  Integer.parseInt(servingSizeField.getText()), 
    					  ingredients);
    	/*
    	 * Return to previous scene
    	 */
	    Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
	    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    
    }
    /*
	 * Return to previous scene
	 */
    @FXML
    void goBackButtonClick(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
	    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
    }
    /*
     * Update the current scene with the updated list view data
	 *@Override
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Populate the ChoiceBox with ingredients
		ingredientArray = getSavedIngredientsArray();
		for(int i = 0; i < ingredientArray.size(); i++) {
	    	dropDownMenu.getItems().add(ingredientArray.get(i).getName());
	    }
		dropDownMenu.setOnAction(this::getIngredient);
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
	/*
	 * A method to help display the selected ingredients in a list view
	 */
	public void getIngredient(ActionEvent event) {
		String myIngredient = dropDownMenu.getValue();
		ingredientsListView.getItems().add(new Ingredient(myIngredient,true));
		newRecipeIngredients.add(new Ingredient(myIngredient,true));
	}  
	
}