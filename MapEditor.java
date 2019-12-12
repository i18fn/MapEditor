import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
/**
 * MapEditorクラス
 * ・レイアウトの配置、管理
 * ・各メニューのメソッドの管理
 * ・ファイルのオープン、保存
 */

public class MapEditor extends Application {
    /* 現在の状態(描画モードが消しゴムモード)を表す列挙型 */
    private enum MouseFlag {
        draw,
        erase
    };
    /* 初期設定では描画モード */
    private MouseFlag mFlag = MouseFlag.draw;
    private boolean saveFlag = false;
    /* マップフィールドの幅と高さの最大値 */
    private final int ROW_MAX = 46;
    private final int COLUMN_MAX = 29;
    /* 配置するマップチップの二次元配列 */
    private MapChip[][] mapField = new MapChip[ROW_MAX][COLUMN_MAX];
    /* パレット(マップチップ)の情報 */
    private Image[] mapChips = new Image[9];
    private ImageView[] palette = new ImageView[9];
    /* 現在の配置するマップチップの情報 */
    private int nowChipNumber = 1;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("マップエディタ");
        stage.setWidth(1698);
        stage.setHeight(1006);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("ファイル");
        Menu editMenu = new Menu("編集");
        MenuItem makeNewFile = new MenuItem("新規作成");
        MenuItem openFile = new MenuItem("開く");
        MenuItem saveFile = new MenuItem("保存");
        MenuItem endEdit = new MenuItem("終了");
        MenuItem eraser = new MenuItem("消しゴム");
        MenuItem allDelete = new MenuItem("全消去");
        makeNewFile.setOnAction(event -> newFile());
        openFile.setOnAction(event -> openFile(stage));
        saveFile.setOnAction(event -> saveFile(stage));
        endEdit.setOnAction(event -> endEdit(stage));
        eraser.setOnAction(event -> modeChange());
        allDelete.setOnAction(event -> fieldDelete());
        fileMenu.getItems().addAll(makeNewFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete, eraser);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane gridPane = new GridPane();
        GridPane palettePane = new GridPane();

        initMapChips();
        initMapField(gridPane);
        initPalette(palettePane);

        Label lblMapChip = new Label("マップチップ");
        lblMapChip.setFont(new Font(25));
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        VBox root = new VBox();
        vBox.getChildren().addAll(lblMapChip, palettePane);
        hBox.getChildren().addAll(gridPane, vBox);
        root.getChildren().addAll(menuBar, hBox);
        Scene scene = new Scene(root);
        scene.setOnMouseDragged(event -> mouseOnAction(event));
        stage.setOnCloseRequest(event -> endEdit(stage, event));
        stage.setScene(scene);
        stage.show();
    }
    private void initPalette(GridPane palettePane) {
        int a = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                palettePane.add(palette[a], j, i);
                a++;
            }
        }
    }
    private void initMapChips() {
        int size = 52;
        mapChips[0] = new Image("mapChip\\0.bmp");
        mapChips[1] = new Image("mapChip\\1.bmp");
        mapChips[2] = new Image("mapChip\\2.bmp");
        mapChips[3] = new Image("mapChip\\3.bmp");
        mapChips[4] = new Image("mapChip\\4.bmp");
        mapChips[5] = new Image("mapChip\\5.bmp");
        mapChips[6] = new Image("mapChip\\6.bmp");
        mapChips[7] = new Image("mapChip\\1.bmp");
        mapChips[8] = new Image("mapChip\\2.bmp");
        
        palette[1] = new MapChip(new Image("mapChip\\1.bmp", size, size, true, false));
        palette[2] = new MapChip(new Image("mapChip\\2.bmp", size, size, true, false));
        palette[3] = new MapChip(new Image("mapChip\\3.bmp", size, size, true, false));
        palette[4] = new MapChip(new Image("mapChip\\4.bmp", size, size, true, false));
        palette[5] = new MapChip(new Image("mapChip\\5.bmp", size, size, true, false));
        palette[6] = new MapChip(new Image("mapChip\\6.bmp", size, size, true, false));
        palette[7] = new MapChip(new Image("mapChip\\1.bmp", size, size, true, false));
        palette[8] = new MapChip(new Image("mapChip\\2.bmp", size, size, true, false));

        palette[1].setOnMouseClicked(event -> chipChange(1));
        palette[2].setOnMouseClicked(event -> chipChange(2));
        palette[3].setOnMouseClicked(event -> chipChange(3));
        palette[4].setOnMouseClicked(event -> chipChange(4));
        palette[5].setOnMouseClicked(event -> chipChange(5));
        palette[6].setOnMouseClicked(event -> chipChange(6));
        palette[7].setOnMouseClicked(event -> chipChange(7));
        palette[8].setOnMouseClicked(event -> chipChange(8));
    }
    private void chipChange(int chip) {
        nowChipNumber = chip;
        System.out.println(nowChipNumber);
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
    private void mouseOnAction(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (mFlag) {
        case draw:
            draw(x, y);
            break;
        case erase:
            delete(x, y);
            break;
        }
    }
    private void draw(int x, int y) {
        // MenuBarの長さ分yを調整
        x /= 32;
        y = (y - 20) / 32;
        if (saveFlag == false) {
            saveFlag = true;
        }
        try {
            mapField[x][y].setImage(mapChips[nowChipNumber]);
            mapField[x][y].setFieldNumber(nowChipNumber);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }
    private void delete(int x, int y) {
        x /= 32;
        y = (y - 20) / 32;
        try {
            mapField[x][y].setImage(mapChips[0]);
            mapField[x][y].setFieldNumber(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }
    private void newFile() {
        
    }

    /* ファイルオープン用の処理 */
    private void openFile(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        inputToFile(fc.showOpenDialog(null));
    }
    private void inputToFile(File file) {
        try {
            if (file != null) {
                FileReader fileReader = new FileReader(file);
                int data;
                int row = 0;
                int column = 0;
                while ((data = fileReader.read()) != -1) {
                    if (data == '\n') {
                        column++;
                        row = 0;
                    } else {
                        data = data & 0x0F;
                        mapField[row][column].setImage(mapChips[data]);
                        mapField[row][column].setFieldNumber(data);
                        row++;                        
                    }
                }
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /* ファイル保存用の処理 */
    private void saveFile(Stage stage){
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        outputToFile(fc.showSaveDialog(null), mapInfoToString());
        saveFlag = false;
    }
    private void outputToFile(File file, String str) {
        try {
            if (file != null) {
                if (file.exists() == false) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(str);
                fileWriter.close();
            } else {
                return;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private String mapInfoToString() {
    /* mapinfoの要素をStringに変換するメソッド */
        String mapData = "";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < COLUMN_MAX; i++) {
            for (int j = 0; j < ROW_MAX; j++) {
                buf.append(String.valueOf(mapField[j][i].getFieldNumber()));
            }
            buf.append("\n");
        }
        mapData = buf.toString();
        return mapData;       
    }

    private void endEdit(Stage stage) {
        if (saveFlag) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("マップエディタ");
            alert.setHeaderText(null);
            alert.setContentText("保存されていませんが終了しますか？");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            } else {
                return;
            }
        } else {
            Platform.exit();
        }
    }
    private void endEdit(Stage stage, WindowEvent event) {
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
    private void modeChange() {
        switch (mFlag) {
        case draw:
            mFlag = MouseFlag.erase;
            break;
        case erase:
            mFlag = MouseFlag.draw;
            break;
        }
    }
    private void fieldDelete() {
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