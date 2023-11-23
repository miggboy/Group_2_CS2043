package group_2_cs2043.Frontend;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import group_2_cs2043.Backend.Recipe;
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

/**
 * This class is the controller for quickSearch.fxml.
 * It showcases the Quick Search feature.
 * @author Miguel Daigle Gould
 */

public class QuickSearchController implements Initializable{
	
	//Components for TableView
	@FXML
	private TableView<Recipe> recipeTable;
	@FXML
	private TableColumn<Recipe, String> nameColumn;
	@FXML
	private TableColumn<Recipe, Duration> prepTimeColumn;
	@FXML
	private TableColumn<Recipe, Integer> servingCountColumn;
	@FXML
	private TableColumn<Recipe, Boolean> favoriteColumn;
	
	@FXML
	ChoiceBox<String> ingredientBox;
	
	Runtime runtime = new Runtime();		//Runtime to interface with Backend
	
	ObservableList<String> ingList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ingList = FXCollections.observableArrayList();
		for(int i = 0; i < runtime.ingredientCount(); i++) {
			ingList.add(runtime.getIngredient(i).getName());
		}
		
		ingredientBox.getItems().addAll(ingList);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
		prepTimeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Duration>("prepTime"));
		servingCountColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("servingCount"));
		favoriteColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Boolean>("favorite"));
		
		recipeTable.setOnMouseClicked(event ->{
			if(event.getClickCount() == 2) {
				try { onRecipeClick(recipeTable.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
				}
			}
		});
		
		ingredientBox.setOnAction(this::findRecipes);
	}
	
	/**
	 * This method retrieves and displays a list of recipes based on input ingredient.
	 */
	
	public void findRecipes(ActionEvent event) {
		String ingName = ingredientBox.getValue();
		ArrayList<Recipe> recArr = runtime.quickSearch(ingName, false);
		ObservableList<Recipe> recList = FXCollections.observableArrayList();
		recList.addAll(recArr);
		recipeTable.setItems(recList);
	}
	
    /**
     * This method opens up the recipe in a pop-up, and shows all recipe details.
     * @throws IOException 
     */
    @FXML
    public void onRecipeClick(Recipe rcp) throws IOException {
    	//Get selected Recipe index number
    	int index = -1;
    	for(int i = 0; i < runtime.recipeCount(); i++) {
    		if(runtime.getRecipe(i).getName().equals(rcp.getName())) {
    			index = i;
    			break;
    		};
    	}
    	
    	//Load and open Recipe information screen
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/recipeInformationScreen.fxml"));
        Parent root = loader.load();
    	RecipeInformationController ric = loader.getController();
    	ric.setValue(index);			//Pass index of recipe through to pop-up scene. Vital for populating data.
    	
    	Stage stage = (Stage)(ingredientBox.getScene()).getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
	
	/**
	 * This method returns to the previous scene.
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
}