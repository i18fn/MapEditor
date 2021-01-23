import javafx.application.Application;
import javafx.stage.Stage;
import window.InitWindow;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXMLLoader;

/**
 * @author i18fn
 * @version 0.0.1
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Mapediter");
        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./window/fxml/layout.fxml"));
            VBox root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}