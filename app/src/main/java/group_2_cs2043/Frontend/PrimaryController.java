package group_2_cs2043.Frontend;

import group_2_cs2043.Backend.Ingredient;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/*
 * This is a controller class for the primary screen.
 *
 * @author Miguel Daigle Gould
 */

public class PrimaryController implements Initializable {

  @FXML
  private ListView<Ingredient> ingredientsListView;
  @FXML
  private ListView<Ingredient> selectedListView;
  
  private ArrayList<Ingredient> ingredientArray = new ArrayList<Ingredient>();
  private ObservableList<Ingredient> ingredientList;
  private ObservableList<Ingredient> selectedList;
  private Ingredient currentIngredient;

  /*
   * Method runs upon instantiation. Loads ObservableList with ingredient data, then in turn manipulates two ListView's
   * ingredientsListView: 	provides view of available ingredients (both default and user-added)
   * selectedListView:		provides view of selected ingredients (to be passed into next scene to return matching Recipes)
   */

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    try {
      ingredientArray = Ingredient.loadSavedList();
    } catch (IOException e) {}
    
    ingredientList = FXCollections.observableArrayList(ingredientArray); 	//Instantiate ingredientList with all available ingredient data
    selectedList = FXCollections.observableArrayList(); 					//Instantiate selectedList, initially empty
    
    ingredientsListView.setItems(ingredientList); 	// Set items in ListView to reflect ingredientList ObservableList 
    selectedListView.setItems(selectedList);		// Set items in ListView to reflect selectedList ObservableList
    
    
    // Mouse click event for 'ingredientsListView' ListView that adds selected Ingredient to 'selectedList' ObservableList
    ingredientsListView.setOnMouseClicked(event -> {
    	currentIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null, and is not present in 'selectedList' ObservableList
    	if((currentIngredient != null) && !(selectedList.contains(currentIngredient))) {
    		selectedList.add(currentIngredient);
    	}
    });
    
  
    // Mouse click event for 'selectedListView' ListView that removes selected Ingredient from 'selectedList' ObservableList
    selectedListView.setOnMouseClicked(event -> {
    	currentIngredient = selectedListView.getSelectionModel().getSelectedItem();
    	
    	//Conditional statement verifies Ingredient is not null
    	if(currentIngredient != null) {
    		selectedList.remove(currentIngredient);
    	}
    });
  }

  @FXML
  void addIngredientAction(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/addIngredient.fxml"));
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
