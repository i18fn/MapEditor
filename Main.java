import javafx.application.Application;
import javafx.stage.Stage;
import init.window.InitWindow;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        stage.setTitle("マップエディタ");
        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        InitWindow.initLayout(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}