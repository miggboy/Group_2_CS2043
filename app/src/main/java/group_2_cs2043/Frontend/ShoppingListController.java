package group_2_cs2043.Frontend;

import java.net.URL;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Recipe;
import group_2_cs2043.Backend.Runtime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShoppingListController implements Initializable{
	
	@FXML
	ChoiceBox<String> recipeBox;
	@FXML
	TableView<Recipe> selRecTable;
	@FXML
	TableColumn<Recipe, String> selRecColumn;
	
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