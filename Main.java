import mapfield.*;
import palette.*;
import filecommand.*;
import command.*;
import editorlib.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.Event;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Optional;

public class Main extends Application {
    private boolean saveFlag;
    private boolean mouseMode;
    // trueであれば、描画中
    private Canvas canvas;
    private Palette palette;
    private MacroCommand history;

    public void start(Stage stage) throws Exception {
        saveFlag = false;
        mouseMode = false;
        canvas = new Canvas();
        palette = Palette.getPalette();
        history = new MacroCommand();
        
        stage.setTitle("マップエディタ");
        stage.setWidth(1698);
        stage.setHeight(1024);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("ファイル");
        Menu editMenu = new Menu("編集");
        MenuItem newFile = new MenuItem("新規作成");
        MenuItem openFile = new MenuItem("開く");
        MenuItem saveFile = new MenuItem("保存");
        MenuItem endEdit = new MenuItem("終了");
        MenuItem allDelete = new MenuItem("全消去");
        // newFile.setOnAction(event -> makeNewFile());
        openFile.setOnAction(event -> openFile());
        saveFile.setOnAction(event -> saveFile());
        endEdit.setOnAction(event -> endEdit(stage, event));
        // allDelete.setOnAction(event -> fieldAllDelete());
        fileMenu.getItems().addAll(newFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane palettePane = new GridPane();
        GridPane canvasPane = new GridPane();
        GridPane buttonPane = new GridPane();

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setMinSize(50, 50);
        initPalette(palettePane);
        initCanvas(canvasPane);
        initButtons(buttonBar, stage);

        Label lblNowChip = new Label("現在のマップチップ  ");
        lblNowChip.setFont(new Font(20));
        HBox nowChipPane = new HBox();
        ImageView nowChip = new ImageView(palette.getNowImage()); 
        nowChipPane.getChildren().addAll(lblNowChip, nowChip);

        Label lblMapChip = new Label("マップチップ");
        lblMapChip.setFont(new Font(25));
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox root = new VBox();
        vBox.getChildren().addAll(lblMapChip, palettePane, nowChipPane, buttonPane);
        vBox.setSpacing(5.0);
        hBox.getChildren().addAll(canvasPane, vBox);
        root.getChildren().addAll(menuBar, buttonBar, hBox);
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(event -> endEdit(stage, event));
        stage.setScene(scene);
        stage.show();
    }

    private void initPalette(GridPane palettePane) {
        int length = palette.getPaletteLength();
        String url;
        for (int i = 0; i < length; i++) {
            url = "mapChip\\";
            url = url + "\\" + String.valueOf(i) + ".bmp";
            palette.initPalette(url, i);
        }        
        int a = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                palettePane.add(palette.paletteIv[a], j, i);
                a++;
            }
        }
    }

    private void initCanvas(GridPane canvasPane) {
        for (int i = 0; i < canvas.WIDTH_MAX; i++) {
            for (int j = 0; j < canvas.HEIGHT_MAX; j++) {
                canvas.mapField[i][j] = new MapChip(palette.getImage(0), 0);
                GridPane.setConstraints(canvas.mapField[i][j], i, j);
                canvasPane.getChildren().addAll(canvas.mapField[i][j]);
                canvasPane.setOnMouseClicked(event -> draw(event));
                canvasPane.setOnMouseDragged(event -> draw(event));
            }
        }
    }

    private void initButtons(ButtonBar buttonBar, Stage stage) {
        ButtonX btnSave = new ButtonX(32, 32, "btnSave.png");
        ButtonX btnUndo = new ButtonX(32, 32, "btnUndo.png");
        btnSave.setOnAction(event -> saveFile());
        btnUndo.setOnAction(event -> undo());
        buttonBar.getButtons().addAll(btnSave, btnUndo);
    }

    private void draw(MouseEvent event) {   
        if (!mouseMode) {
            // 描画中でなければ、(!mouseModeなら) 変更前の盤面を保存する
            history.add(new saveFieldCommand(canvas));
            mouseMode = true;
        } else {
            if (String.valueOf(event.getEventType()).equals("MOUSE_CLICKED")) {
                mouseMode = false;
            }
        }
        DrawCommand cmd = new DrawCommand(canvas, event);
        cmd.execute();
        saveFlag = cmd.getFlag();
        if (this.saveFlag == false) {
            this.saveFlag = false;
        } 
    }

    private void undo() {
        history.undo();
    }

    private void openFile() {
        OpenFile of = new OpenFile();
        of.openFile(canvas);
    }

    private void saveFile() {
        SaveFile sf = new SaveFile();
        sf.saveFile(canvas);
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