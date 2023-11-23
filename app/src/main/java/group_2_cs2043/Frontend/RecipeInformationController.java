package group_2_cs2043.Frontend;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * This controller showcases all data relating to a Recipe object.
 * @author Miguel Daigle Gould
 */

public class RecipeInformationController implements Initializable{
	
	//Displays for various Recipe attributes
	@FXML
	private Label nameLabel;
	@FXML
	private Label prepTimeLabel;
	@FXML
	private Label servingCountLabel;
	@FXML
	private Label ratingLabel;
	@FXML
	private Label missingLabel;
	@FXML
	private TextArea instructionTextArea;
	@FXML
	private TableView<RecipeIngredient> ingredientTable;
	@FXML
	private TableColumn<RecipeIngredient, String> ingredientsColumn;
	
	@FXML
	private CheckBox favTick;			//Checkbox for adding a recipe to Favorites
	
	//Components for adding ratings
	@FXML
	private Label errorLabel;
	@FXML
	private TextField rateField;
	
	private int index;
	private Runtime runtime = new Runtime();
	private Recipe recipe;
	
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
		dispAverageRating();
		
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
	
	/**
	 * This method modifies the isFavorite attribute of a recipe depending on user input
	 * @throws IOException
	 */
	
	@FXML
	public void setFavorite() throws IOException {
		Recipe rec = runtime.getRecipe(index);		
		if(!favTick.isSelected()) {
			runtime.setFavorite(rec.getName(), false);
		}
		else {
			runtime.setFavorite(rec.getName(), true);	
		}
	}
	
	/**
	 * This method adds a rating to the recipe.
	 * @throws IOException
	 */
	public void addRating() throws IOException {
		try {
			int rate = Integer.parseInt(rateField.getText());
			if(rate > 5 || rate < 0) throw new NumberFormatException();
			runtime.getRecipe(index).addRating(rate);
			dispAverageRating();
			rateField.setText("");
			errorLabel.setText("");
		}
		catch(NumberFormatException e) {
			errorLabel.setText("Bad input. Please enter 0-5.");
		}
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
	
	/**
	 * This method removes a recipe from runtime, then returns to the previous scene.
	 * @throws IOException
	 */
	@FXML
	public void onDeleteClick(ActionEvent event) throws IOException {
		recipe = runtime.getRecipe(index);
		runtime.removeRecipe(recipe.getName());
		onReturnClick(event);
	}
	
	/**
	 * Helper method for calculating and displaying average rating.
	 */
	public void dispAverageRating() {
		Double avgRate = recipe.getAverageRating();
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		ratingLabel.setText(decimalFormat.format(avgRate));
	}
}
