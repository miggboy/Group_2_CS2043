package group_2_cs2043.Frontend;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is a controller class for the title screen.
 *
 * @author Miguel Daigle Gould
 */
public class TitleController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  /**
   * This method executes when a user clicks the "Start" button.
   * It serves to transition into the primary scene.
   * @throws IOException
   */
  @FXML
  protected void onStartClick(ActionEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("/primary.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
