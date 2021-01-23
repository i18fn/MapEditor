import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import property.Property;

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

        Property appProperty = Property.getProperty();
        
        loadFXML(stage);

        stage.show();
    }

    public void loadFXML(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./window/fxml/layout.fxml"));
            VBox root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}