package group_2_cs2043.Frontend;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import group_2_cs2043.Backend.Runtime;
import group_2_cs2043.Backend.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

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
	
    @FXML
    private Button recipeScreenBack;
	
	private ObservableList<Recipe> recipeList;
	private Runtime runtime = new Runtime();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ArrayList<Recipe> tempArray = new ArrayList<Recipe>();
		
		int TEMP = 5; 			//This is a placeholder int representing missing ingredient tolerance
		
		recipeList = FXCollections.observableArrayList();	//Instantiate recipeList with all Recipe data
		
		//This loop appends all recipes up to a maximum missing number (TEMP for now). Least missing ones go first, then increment.
		
		for(int i = 0; i < TEMP; i++) {
			tempArray = runtime.getRecipes(i);
			recipeList.addAll(tempArray);
		}
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));;
		prepTimeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Duration>("prepTime"));
		servingCountColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("servingCount"));
		
		recipeTable.setItems(recipeList);
	}
	
	/**
	 * Go back to the previous screen
	 */
    @FXML
    void recipeScreenBackClick(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
	    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
    }
}
