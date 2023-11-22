package group_2_cs2043.Frontend;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import group_2_cs2043.Backend.Ingredient;
import group_2_cs2043.Backend.Recipe;
import group_2_cs2043.Backend.Runtime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecipeInformationController implements Initializable{
	
	@FXML
	Label nameLabel;
	@FXML
	Label prepTimeLabel;
	@FXML
	Label servingCountLabel;
	@FXML
	Label ratingLabel;
	@FXML
	TextArea instructionTextArea;
	@FXML
	TableView<Ingredient> ingredientTable;
	@FXML
	TableColumn<Ingredient, String> ingredientsColumn;
	@FXML
	TableColumn<Ingredient, Boolean> availabilityColumn;
	
    private ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
	
	int index;
	Runtime runtime = new Runtime();
	Recipe recipe;
	
	public void setValue(int index) {
		this.index = index;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recipe = runtime.getRecipe(index);			//Retrieve indexed recipe from runtime
		
		//Populate labels and text area with data
		nameLabel.setText(recipe.getName()); 
		prepTimeLabel.setText(recipe.getPrepTime().toString());
		servingCountLabel.setText(""+recipe.getServingCount());
		ratingLabel.setText(""+recipe.getAverageRating()+"/5");
		instructionTextArea.setText(recipe.getInstructions());
		
		//Set column values
		ingredientsColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
		availabilityColumn.setCellValueFactory(new PropertyValueFactory<Ingredient, Boolean>("isAvailable"));
	}
}
