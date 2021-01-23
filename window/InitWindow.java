package window;

import javafx.stage.Stage;
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
 * 画面を設定する関数群
 * 
 * @author i18fn
 */
public class InitWindow {
    public static void initLayout(Stage stage) {
        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource("./fxml/layout.fxml"));
        //     VBox root = loader.load();

        //     Scene scene = new Scene(root);
        //     stage.setScene(scene);
        //     stage.show();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // stage.setTitle("マップエディタ");
        // stage.setMinWidth(1024);
        // stage.setMinHeight(768);

        // MenuBar menuBar = new MenuBar();
        // initMenuBar(menuBar);

        // ButtonBar buttonBar = new ButtonBar();
        // initButtonBar(buttonBar);

        // ScrollPane canvaScrollPane = new ScrollPane();
        // GridPane palettePane = new GridPane();
        // GridPane canvasPane = new GridPane();
        // GridPane buttonPane = new GridPane();
        // HBox nowChipPane = new HBox();
        // HBox hBox = new HBox();
        // VBox vBox = new VBox();
        // VBox root = new VBox();

        // // canvaScrollPane.getChildren().addAll(canvasPane);
        // vBox.getChildren().addAll(palettePane, nowChipPane, buttonPane);
        // vBox.setSpacing(5.0);
        // hBox.getChildren().addAll(canvasPane, vBox);
        // root.getChildren().addAll(menuBar, buttonBar, hBox);

        // // stage.setOnCloseRequest(event -> endEdit(stage, event));
        // Scene scene = new Scene(root);
        // stage.setScene(scene);
    }

    private static void initMenuBar(MenuBar menuBar) {
        // // MenuBarの名前や挙動などの設定
        // Menu fileMenu = new Menu("ファイル");
        // Menu editMenu = new Menu("編集");
        // MenuItem newFileiItem = new MenuItem("新規作成");
        // MenuItem openFileiItem = new MenuItem("開く");
        // MenuItem saveFileiItem = new MenuItem("保存");
        // MenuItem endEditItem = new MenuItem("終了");
        // MenuItem allDeleteItem = new MenuItem("全消去");
        // MenuItem undoItem = new MenuItem("元に戻す");
        // MenuItem redoItem = new MenuItem("やり直し");
        // fileMenu.getItems().addAll(newFileiItem, openFileiItem, saveFileiItem, endEditItem);
        // editMenu.getItems().addAll(undoItem, redoItem, allDeleteItem);
        // menuBar.getMenus().addAll(fileMenu, editMenu);
    }

    private static void initButtonBar(ButtonBar buttonBar) {
        // Button btnNew = new Button();
        // Button btnOpen = new Button();
        // Button btnSave = new Button();
        // Button btnDelete = new Button();
        // Button btnUndo = new Button();
        // Button btnRedo = new Button();
        // Button btnPlay = new Button();
        // buttonBar.getButtons().addAll(btnNew, btnOpen, btnSave, btnDelete, btnUndo, btnRedo, btnPlay);
    }
}
