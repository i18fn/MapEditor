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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.util.Optional;

public class MapEditor extends Application {
    private boolean saveFlag = false;
    /* マップフィールドの幅と高さの最大値 */
    private final int ROW_MAX = 46;
    private final int COLUMN_MAX = 29;
    /* 配置するマップチップの二次元配列 */
    private MapChip[][] mapField = new MapChip[ROW_MAX][COLUMN_MAX];
    /* パレット(マップチップ)の情報 */
    private Image[] mapChips = new Image[16];
    private ImageView[] palette = new ImageView[16];
    /* 現在の配置するマップチップの情報 */
    private int nowChipNumber = 1;
    private ImageView nowChip;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("マップエディタ");
        stage.setWidth(1698);
        stage.setHeight(1006);
        // stage.setMaxWidth(1698);
        // stage.setMaxHeight(1006);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("ファイル");
        Menu editMenu = new Menu("編集");
        MenuItem makeNewFile = new MenuItem("新規作成");
        MenuItem openFile = new MenuItem("開く");
        MenuItem saveFile = new MenuItem("保存");
        MenuItem endEdit = new MenuItem("終了");
        MenuItem allDelete = new MenuItem("全消去");
        makeNewFile.setOnAction(event -> newFile());
        openFile.setOnAction(event -> openFile());
        saveFile.setOnAction(event -> saveFile());
        endEdit.setOnAction(event -> endEdit(stage, event));
        allDelete.setOnAction(event -> fieldAllDelete());
        fileMenu.getItems().addAll(makeNewFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane gridPane = new GridPane();
        GridPane palettePane = new GridPane();
        GridPane buttonPane = new GridPane();

        initMapChips();
        initMapField(gridPane);
        initPalette(palettePane);
        initButtons(buttonPane);

        Label lblNowChip = new Label("現在のマップチップ  ");
        lblNowChip.setFont(new Font(20));
        nowChip = new ImageView(mapChips[nowChipNumber]);
        HBox nowChipPane = new HBox();
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
        hBox.getChildren().addAll(gridPane, vBox);
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
        ButtonX btnSave = new ButtonX(width, height);
        ButtonX btnUndo = new ButtonX(width, height);
        ButtonX btnRedo = new ButtonX(width, height);
        ButtonX btnChipSet = new ButtonX(width, height);
        ButtonX btnFill = new ButtonX(width, height);
        ButtonX btnLine = new ButtonX(width, height);
        ButtonX btnRect = new ButtonX(width, height);
        ButtonX btnRectFill = new ButtonX(width, height);

        buttonPane.add(btnSave, 0, 0);
        buttonPane.add(btnUndo, 1, 0);
        buttonPane.add(btnRedo, 2, 0);
        buttonPane.add(btnChipSet, 3, 0);
        buttonPane.add(btnFill, 0, 1);
        buttonPane.add(btnLine, 1, 1);
        buttonPane.add(btnRect, 2, 1);
        buttonPane.add(btnRectFill, 3, 1);
    }
    private void initPalette(GridPane palettePane) {
        int a = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                palettePane.add(palette[a], j, i);
                a++;
            }
        }
    }
    private void initMapChips() {
        int size = 52;
        String url;
        for (int i = 0; i < palette.length; i++) {
            final int I = i;
            url = "mapchip\\";
            url = url + "\\" + String.valueOf(i) + ".bmp";
            palette[i] = new MapChip(new Image(url, size, size, true, false));
            palette[i].setOnMouseClicked(event -> chipChange(I));
        }
        for (int i = 0; i < mapChips.length; i++) {
            url = "mapchip\\";
            url = url + "\\" + String.valueOf(i) + ".bmp";
            mapChips[i] = new Image(url);
        }
    }
    private void initMapField(GridPane gridPane) {
        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COLUMN_MAX; j++) {
                mapField[i][j] = new MapChip(mapChips[0], 0);
                GridPane.setConstraints(mapField[i][j], i, j);
                gridPane.getChildren().addAll(mapField[i][j]);
            }
        }
    }
    private void chipChange(int chip) {
        nowChipNumber = chip;
        nowChip.setImage(mapChips[chip]);
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
        try {
            mapField[x][y].setImage(mapChips[nowChipNumber]);
            mapField[x][y].setFieldNumber(nowChipNumber);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        if (saveFlag == false) {
            saveFlag = true;
        }
    }
    
    private void newFile() {}

    private void openFile() {
        OpenFile of = new OpenFile();
        of.openFile(mapField, mapChips);
    }

    private void saveFile(){
        SaveFile sv = new SaveFile();
        sv.saveFile(mapField);
        saveFlag = false;
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

    private void fieldAllDelete() {
        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COLUMN_MAX; j++) {
                mapField[i][j].setImage(mapChips[0]);
                mapField[i][j].setFieldNumber(0);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}