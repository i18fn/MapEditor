import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Optional;

public class MapEditor extends Application {
    private boolean saveFlag = false;
    private EditorInfo editorInfo = new EditorInfo(1);

    public void start(Stage stage) throws Exception {
        stage.setTitle("マップエディタ");
        stage.setWidth(1698);
        stage.setHeight(1006);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("ファイル");
        Menu editMenu = new Menu("編集");
        MenuItem newFile = new MenuItem("新規作成");
        MenuItem openFile = new MenuItem("開く");
        MenuItem saveFile = new MenuItem("保存");
        MenuItem endEdit = new MenuItem("終了");
        MenuItem allDelete = new MenuItem("全消去");
        newFile.setOnAction(event -> makeNewFile());
        openFile.setOnAction(event -> openFile());
        saveFile.setOnAction(event -> saveFile());
        endEdit.setOnAction(event -> endEdit(stage, event));
        allDelete.setOnAction(event -> fieldAllDelete());
        fileMenu.getItems().addAll(newFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane fieldPane = new GridPane();
        GridPane palettePane = new GridPane();
        GridPane buttonPane = new GridPane();

        editorInfo.init(palettePane, fieldPane);
        initButtons(buttonPane);

        Label lblNowChip = new Label("現在のマップチップ  ");
        lblNowChip.setFont(new Font(20));
        HBox nowChipPane = new HBox();
        nowChipPane.getChildren().addAll(lblNowChip, editorInfo.nowChip);

        Label lblLog = new Label("ログ");
        lblLog.setFont(new Font(20));
        TextArea logArea = new TextArea();
        logArea.setWrapText(true);
        logArea.setPrefHeight(490);
        logArea.setEditable(false);

        Label lblMapChip = new Label("マップチップ");
        lblMapChip.setFont(new Font(25));
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox root = new VBox();
        vBox.getChildren().addAll(lblMapChip, palettePane, nowChipPane, buttonPane, lblLog, logArea);
        vBox.setSpacing(5.0);
        hBox.getChildren().addAll(fieldPane, vBox);
        root.getChildren().addAll(menuBar, hBox);
        Scene scene = new Scene(root);
        scene.setOnMouseClicked(event -> mouseOnAction(event));
        scene.setOnMouseDragged(event -> mouseOnAction(event));
        stage.setOnCloseRequest(event -> endEdit(stage, event));
        stage.setScene(scene);
        stage.show();
    }
    private void initButtons(GridPane buttonPane) {
        double width = 52;
        double height = width;
        ButtonX btnSave = new ButtonX(width, height, "btnSave.png");
        ButtonX btnUndo = new ButtonX(width, height, "btnUndo.png");
        ButtonX btnRedo = new ButtonX(width, height, "btnRedo.png");
        ButtonX btnChipSet = new ButtonX(width, height, "btnChipSet.png");
        ButtonX btnFill = new ButtonX(width, height, "btnFill.png");
        ButtonX btnLine = new ButtonX(width, height, "btnLine.png");
        ButtonX btnRect = new ButtonX(width, height, "btnRect.png");
        ButtonX btnRectFill = new ButtonX(width, height, "btnRectFill.png");

        buttonPane.add(btnSave, 0, 0);
        buttonPane.add(btnUndo, 1, 0);
        buttonPane.add(btnRedo, 2, 0);
        buttonPane.add(btnChipSet, 3, 0);
        buttonPane.add(btnFill, 0, 1);
        buttonPane.add(btnLine, 1, 1);
        buttonPane.add(btnRect, 2, 1);
        buttonPane.add(btnRectFill, 3, 1);

        btnSave.setOnAction(event -> saveFile());
    }
    private void mouseOnAction(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        // MenuBarの長さ分yを調整
        x /= 32;
        y = (y - 20) / 32;
        draw(x, y);
    }
    private void draw(int x, int y) {
        int errorflag = editorInfo.draw(x, y);
        if (errorflag == -1 && saveFlag == false) {
            saveFlag = true;
        }
    }
    private void makeNewFile() {
        editorInfo.row = 40;
        editorInfo.column = 20;
        editorInfo.fieldHiding();
    }
    private void openFile() {
        OpenFile of = new OpenFile();
        of.openFile(editorInfo.mapField, editorInfo.mapChips);
    }
    private void saveFile(){
        SaveFile sv = new SaveFile();
        sv.saveFile(editorInfo.mapField);
        saveFlag = false;
    }
    private void fieldAllDelete() {
        editorInfo.fieldAllDelete();
    }
    private void endEdit(Stage stage, Event event) {
        if (saveFlag) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("マップエディタ");
            alert.setHeaderText(null);
            alert.setContentText("保存されていませんが終了しますか？");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            } else {
                event.consume();
            }
        } else {
            Platform.exit();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}