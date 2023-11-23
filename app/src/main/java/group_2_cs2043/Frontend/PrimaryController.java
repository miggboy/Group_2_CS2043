package group_2_cs2043.Frontend;

import group_2_cs2043.Backend.Ingredient;
import group_2_cs2043.Backend.Runtime;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a controller class for the primary screen.
 *
 * @author Miguel Daigle Gould
 */

public class PrimaryController implements Initializable {

  //FXML variables for interactivity
  @FXML
  private TextField ingredientField;
  @FXML
  private ListView<Ingredient> ingredientsListView;
  @FXML
  private ListView<Ingredient> selectedListView;
  
  private Runtime runtime = new Runtime();
  
  //Variables for displaying and selecting Ingredients
  private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
  private ArrayList<Ingredient> selectedArray = new ArrayList<Ingredient>();
  private ObservableList<Ingredient> ingredientList;
  private ObservableList<Ingredient> selectedList;
  private Ingredient currentIngredient;

  /**
   * Method runs upon instantiation. Loads ObservableList with ingredient data, then in turn manipulates two ListView's
   * ingredientsListView: 	provides view of available ingredients (both default and user-added)
   * selectedListView:		provides view of selected ingredients (to be passed into next scene to return matching Recipes)
   */
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1){
	  
	ingredientArray = getSavedIngredientsArray();
    
    //Load all available ingredients, derived from existing ingredients
    for(int i = 0; i < ingredientArray.size(); i++) {
    	if(ingredientArray.get(i).isAvailable())
    		selectedArray.add(ingredientArray.get(i));
    }
    
    ingredientList = FXCollections.observableArrayList(ingredientArray); 	//Instantiate ingredientList with existing ingredients
    selectedList = FXCollections.observableArrayList(selectedArray); 		//Instantiate selectedList with available ingredients
    
    ingredientsListView.setItems(ingredientList); 	// Set items in ListView to reflect ingredientList ObservableList 
    selectedListView.setItems(selectedList);		// Set items in ListView to reflect selectedList ObservableList
    
    
    // Mouse click event for 'ingredientsListView' ListView that adds selected Ingredient to 'selectedList' ObservableList
    ingredientsListView.setOnMouseClicked(event -> {
    	currentIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null, and is not present in 'selectedList' ObservableList
    	if((currentIngredient != null) && !(selectedList.contains(currentIngredient))) {
    		selectedList.add(currentIngredient);
    		try {
				runtime.setAvailable(currentIngredient.getName());
			} catch (IOException e) {}
    	}
    	currentIngredient = null;
    });
    
  
    // Mouse click event for 'selectedListView' ListView that removes selected Ingredient from 'selectedList' ObservableList
    selectedListView.setOnMouseClicked(event -> {
    	currentIngredient = selectedListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null
    	if(currentIngredient != null) {
    		selectedList.remove(currentIngredient);
    		try {
				runtime.setUnAvailable(currentIngredient.getName());
			} catch (IOException e) {}
    	}
    	currentIngredient = null;
    });
     
  }
  
  /**
   * Helper method for resetting Ingredients list. To be removed before merge with main.
   */
  public void resetList() {
	 ingredientArray = Ingredient.makeDefaultList();
	 try {
		Ingredient.writeCurrentList(ingredientArray);
	} catch (IOException e) {
		e.printStackTrace();
	}	
	 ingredientList.clear();
	 ingredientList.addAll(ingredientArray);
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
  
  /**
  * This method opens a pop-up window to add a new Ingredient.
  */
  
  @FXML
  public void onAddNewIngredientClick(ActionEvent event) throws IOException {
	  Parent root = FXMLLoader.load(getClass().getResource("/addIngredient.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	  
	  
	  /*
	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addIngredient.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setTitle("Add New Ingredient");
      
      String str = getClass().getResource("/IMG/icon.png").toString();
      Image icon = new Image(str);
      stage.getIcons().add(icon);
      
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();									//Program will wait until pop-up is closed.
      
      //Clearing list and refilling is necessary to refresh ListView
      ArrayList<Ingredient> arr = new ArrayList<Ingredient>();
	  for(int i = 0; i < runtime.ingredientCount(); i++) {
		  arr.add(runtime.getIngredient(i));
	  }
	  
	  ingredientList.clear();
	  ingredientList.addAll(arr);
	  */
  }
  
  /**
   * This method removes selected Ingredient from the existing Ingredients list
 * @throws IOException 
   */
  @FXML
  public void onRemoveIngredientClick() throws IOException {
	  Ingredient ingr = ingredientsListView.getSelectionModel().getSelectedItem();
	  ingredientList.remove(ingr);
	  selectedList.remove(ingr);
	  runtime.removeIngredient(ingr.getName());
  }
  
  
  /**
   * This method clears the selectedList ObservableList, thus clearing the corresponding ListView
   */
  @FXML
  public void onClearSelectionClick() throws IOException{
	for(int i = 0; i < selectedList.size(); i++) {
		runtime.setUnAvailable(selectedList.get(i).getName());
	}
	selectedList.clear();  
  }
  
  /**
   * This method opens the Recipe screen.
   */
  @FXML
  public void onConfirmIngredients(ActionEvent event) throws IOException{
	  	Parent root = FXMLLoader.load(getClass().getResource("/recipeScreen.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
  }
  @FXML
  void addNewRecipeClick(ActionEvent event) throws IOException {
	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addRecipe.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.showAndWait();
  }

}
