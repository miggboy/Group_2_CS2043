package Frontend;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/*
 * The Main.java class serves as the primary launcher for the application.
 * Employs the use of JavaFX libraries.
 * 
 * @author Miguel Daigle Gould
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("titleScreen.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("CSS/application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false);
	        //primaryStage.initStyle(StageStyle.UNDECORATED);
			URL url = getClass().getResource("IMG/icon.png");
	        String str = url.toString();
	        Image icon = new Image(str);
	        primaryStage.getIcons().add(icon);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Launches the application.
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
