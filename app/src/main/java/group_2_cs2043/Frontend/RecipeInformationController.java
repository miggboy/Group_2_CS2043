package group_2_cs2043.Frontend;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import group_2_cs2043.Backend.Ingredient;
import group_2_cs2043.Backend.Recipe;
import group_2_cs2043.Backend.RecipeIngredient;
import group_2_cs2043.Backend.Runtime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
	Label missingLabel;
	@FXML
	TextArea instructionTextArea;
	@FXML
	TableView<RecipeIngredient> ingredientTable;
	@FXML
	TableColumn<RecipeIngredient, String> ingredientsColumn;
	@FXML
	CheckBox favTick;
	
	
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
		
		if(recipe.isFavorite()) {
			favTick.setSelected(true);
		}
		
		//Populate labels and text area with data
		nameLabel.setText(recipe.getName()); 
		prepTimeLabel.setText(recipe.getPrepTime().toString());
		servingCountLabel.setText(""+recipe.getServingCount());
		instructionTextArea.setText(recipe.getInstructions());
		
		//Format and display rating
		Double avgRate = recipe.getAverageRating();
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		ratingLabel.setText(decimalFormat.format(avgRate));
		
		//Set column values
		ingredientsColumn.setCellValueFactory(new PropertyValueFactory<RecipeIngredient, String>("ingredientName"));
		
		//Create ArrayList of all RecipeIngredients in a Recipe
		ObservableList<RecipeIngredient> ingredientArray = FXCollections.observableArrayList();
		for(int i = 0; i < recipe.getIngredientCount(); i++) {
			ingredientArray.add(recipe.getIngredient(i));
		}
		
		//Pass array into table
		ingredientTable.setItems(ingredientArray);
		
		//Displays missing ingredients list
		ArrayList<RecipeIngredient> recinArray = runtime.getMissingIngredients(recipe);
		
		String disp = "";
		for(int i = 0; i < recinArray.size(); i++) {
			disp += recinArray.get(i).getIngredientName() + ", ";
		}

		missingLabel.setText(disp);
	}
	
	
	@FXML
	public void setFavorite() {
		Recipe rec = runtime.getRecipe(index);		
		if(!favTick.isSelected()) {
			runtime.setFavorite(rec.getName(), false);
		}
		else {
			runtime.setFavorite(rec.getName(), true);	
		}
	}
	
}
