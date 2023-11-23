package group_2_cs2043.Frontend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Recipe;
import group_2_cs2043.Backend.RecipeIngredient;
import group_2_cs2043.Backend.Runtime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ShoppingListController implements Initializable{
	
	@FXML
	ChoiceBox<String> recipeBox;
	@FXML
	TableView<Recipe> selRecTable;
	@FXML
	TableColumn<Recipe, String> selRecColumn;
	@FXML
	TableView<RecipeIngredient> missIngTable;
	@FXML
	TableColumn<RecipeIngredient, String> missIngColumn;
	
	private Runtime runtime = new Runtime();
	private ObservableList<String> recList;
	private ObservableList<Recipe> selList;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recList = FXCollections.observableArrayList();
		selList = FXCollections.observableArrayList();
		
		for(int i = 0; i < runtime.recipeCount(); i++) {
			recList.add(runtime.getRecipe(i).getName());
		}
		recipeBox.setItems(recList);
		
		selRecColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
		selRecTable.setItems(selList);
		
		missIngColumn.setCellValueFactory(new PropertyValueFactory<RecipeIngredient, String>("ingredientName"));
		
		recipeBox.setOnAction(this::addRecipe);
	}
	
	/**
	 * This method adds a recipe to the list.
	 */
	public void addRecipe(ActionEvent event) {
		String name = recipeBox.getValue();
		int ind = findRecipe(name);
		Recipe rec = runtime.getRecipe(ind);
		selList.add(rec);
	}
	
	/**
	 * This method generates and displays a list of missing ingredients.
	 */
	@FXML
	public void onGenerateClick() {
		ArrayList<Recipe> recArrList = new ArrayList<Recipe>();
		recArrList.addAll(selList);
		ArrayList<RecipeIngredient> recIngList= runtime.makeShoppingList(recArrList);
		ObservableList<RecipeIngredient> obsRecIngList = FXCollections.observableArrayList();
		obsRecIngList.addAll(recIngList);
		missIngTable.setItems(obsRecIngList);	
	}
	
	/**
	 * This method returns to the Recipe View scene.
	 * @throws IOException 
	 */
	@FXML
	public void onReturnClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/recipeScreen.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
	
	/**
	 * Helper method for finding a Recipe's index based on name
	 */
	public int findRecipe(String recStr) {
		int index = -1;
		
		for(int i = 0; i < runtime.recipeCount(); i++) {
			if(runtime.getRecipe(i).getName().equals(recStr)) {
				index = i;
			}
		}
		return index;
	}	
}