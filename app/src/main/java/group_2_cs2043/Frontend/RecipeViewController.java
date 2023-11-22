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
import javafx.stage.Modality;
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
		
		int MAX = 7; 			//Missing ingredient tolerance, up to 7 missing ingredients in a recipe
		
		recipeList = FXCollections.observableArrayList();	//Instantiate recipeList with all Recipe data
		
		//This loop appends all recipes up to a maximum missing number. Least missing ones go first, then increment.
		for(int i = 0; i < MAX; i++) {
			tempArray = runtime.getRecipes(i);
			recipeList.addAll(tempArray);
		}
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
		prepTimeColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Duration>("prepTime"));
		servingCountColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("servingCount"));
		
		recipeTable.setItems(recipeList);
		
		recipeTable.setOnMouseClicked(event ->{
			if(event.getClickCount() == 2) {
				try { onRecipeClick();
				} catch (IOException e) {
				}
			}
		});
	}
	
	/**
	 * Go back to the previous screen
	 */
    @FXML
    public void recipeScreenBackClick(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
	    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
    }
    
    /**
     * This method opens up the recipe in a pop-up, and shows all recipe details.
     * @throws IOException 
     */
    @FXML
    public void onRecipeClick() throws IOException {
    	//Get selected Recipe index number
    	Recipe rcp = recipeTable.getSelectionModel().getSelectedItem();
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
    	
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
