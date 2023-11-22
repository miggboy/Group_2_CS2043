package group_2_cs2043.Frontend;

import group_2_cs2043.Backend.Ingredient;
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

  @FXML
  private TextField ingredientField;
  @FXML
  private ListView<Ingredient> ingredientsListView;
  @FXML
  private ListView<Ingredient> selectedListView;
  
  private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
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
	  
    try {
        ingredientArray = Ingredient.loadSavedList();
    } catch (IOException e) {}
   
    ingredientList = FXCollections.observableArrayList(ingredientArray); //Instantiate ingredientList with all available ingredient data				
    selectedList = FXCollections.observableArrayList(); 				 //Instantiate selectedList, initially empty
    
    ingredientsListView.setItems(ingredientList); 	// Set items in ListView to reflect ingredientList ObservableList 
    selectedListView.setItems(selectedList);		// Set items in ListView to reflect selectedList ObservableList
    
    
    // Mouse click event for 'ingredientsListView' ListView that adds selected Ingredient to 'selectedList' ObservableList
    ingredientsListView.setOnMouseClicked(event -> {
    	currentIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null, and is not present in 'selectedList' ObservableList
    	if((currentIngredient != null) && !(selectedList.contains(currentIngredient))) {
    		selectedList.add(currentIngredient);
    	}
    	currentIngredient = null;
    });
    
  
    // Mouse click event for 'selectedListView' ListView that removes selected Ingredient from 'selectedList' ObservableList
    selectedListView.setOnMouseClicked(event -> {
    	currentIngredient = selectedListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null
    	if(currentIngredient != null) {
    		selectedList.remove(currentIngredient);
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
  * This method opens a pop-up window to add a new Ingredient.
  */
  
  @FXML
  public void onAddNewIngredientClick(ActionEvent event) throws IOException {
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
      ingredientList.clear();
      ArrayList<Ingredient> reList = Ingredient.loadSavedList();
      ingredientList.addAll(reList);
  }
  
  /**
   * This method opens a prompt for removing a selected Ingredient.
   * @throws IOException 
   */
  @FXML
  public void onRemoveIngredientClick() throws IOException {
	  currentIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
	  
	  if(currentIngredient != null) {
		  ingredientList.clear();
	      ArrayList<Ingredient> reList = Ingredient.loadSavedList();
	    
	      //.remove() method always returns false.
	      //Might be an issue with .equals not Overriding
	      //Manual iteration here for now
	      for(int i = 0; i < reList.size(); i++) {
	    	  if(reList.get(i).getName().equals(currentIngredient.getName())) {
	    		  reList.remove(i);
	    		  break;
	    	  }
	      }
	      
	      Ingredient.writeCurrentList(reList);
	      ingredientList.addAll(reList);
	      if(selectedList.contains(currentIngredient)) {
	    	  selectedList.remove(currentIngredient);
	      }
	  }
  }
  
  /*
   * This method clears the selectedList ObservableList, thus clearing the corresponding ListView
   */
  @FXML
  public void onClearSelectionClick() {
	selectedList.clear();  
  }
}
