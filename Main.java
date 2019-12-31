import mapfield.*;
import palette.*;
import filecommand.*;
import editorlib.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
    Canvas canvas = new Canvas();
    Palette palette = Palette.getPalette();
    ImageView nowChip;
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
        // newFile.setOnAction(event -> makeNewFile());
        openFile.setOnAction(event -> openFile());
        saveFile.setOnAction(event -> saveFile());
        // endEdit.setOnAction(event -> endEdit());
        // allDelete.setOnAction(event -> fieldAllDelete());
        fileMenu.getItems().addAll(newFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane palettePane = new GridPane();
        GridPane canvasPane = new GridPane();
        GridPane buttonPane = new GridPane();

        initPalette(palettePane);
        initCanvas(canvasPane);
        initButtons(buttonPane, stage);

        Label lblNowChip = new Label("現在のマップチップ  ");
        lblNowChip.setFont(new Font(20));
        HBox nowChipPane = new HBox();
        nowChip = new ImageView(palette.getNowImage()); 
        nowChipPane.getChildren().addAll(lblNowChip, nowChip);

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
        hBox.getChildren().addAll(canvasPane, vBox);
        root.getChildren().addAll(menuBar, hBox);
        Scene scene = new Scene(root);
        // scene.setOnMouseClicked(event -> mouseOnAction(event));
        // scene.setOnMouseDragged(event -> mouseOnAction(event));
        // stage.setOnCloseRequest(event -> endEdit(stage, event));
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
            }
        }
    }
    private void initButtons(GridPane buttonPane, Stage stage) {
        double width = 52;
        double height = width;
        ButtonX btnSave = new ButtonX(width, height, "btnSave.png"); //実装済み
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
    public void openFile() {
        OpenFile of = new OpenFile();
        of.openFile(canvas);
    }
    public void saveFile() {
        SaveFile sf = new SaveFile();
        sf.saveFile(canvas);
    }
    public static void main(String[] args) {
        launch(args);
    }
}