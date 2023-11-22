package group_2_cs2043.Frontend;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecipeViewController implements Initializable {
	
	@FXML
	private TableView<Recipe> recipeTable;
	@FXML
	private TableColumn<Recipe, String> nameColumn;
	@FXML
	private TableColumn<Recipe, Duration> prepTimeColumn;
	@FXML
	private TableColumn<Recipe, Integer> servingCountColumn;
	@FXML
	private TableColumn<Recipe, ArrayList<Integer>> ratingColumn;
	
	private ObservableList<Recipe> recipeList;
	private ArrayList<Recipe> recipeArray;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			recipeArray = Recipe.loadSavedList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		recipeList = FXCollections.observableArrayList(recipeArray);	//Instantiate recipeList with all Recipe data
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));;
		prepTimeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Duration>("prepTime"));
		servingCountColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("servingCount"));
		
		recipeTable.setItems(recipeList);
		
		
		
		
		//Test block. Wipe-out before push
	
		
		
		
		
	}

}
