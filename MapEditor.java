import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    /* マップフィールドの幅と高さの最大値 */
    private final int row = 46;
    private final int column = 29;
    /* 配置するマップチップの二次元配列 */
    private MapChip[][] mapField = new MapChip[row][column];
    /* パレット(マップチップ)の情報 */
    private Image[] mapChips = new Image[9];
    public void start(Stage stage) throws Exception {
        stage.setTitle("マップエディタ");
        stage.setWidth(1880);
        stage.setHeight(1000);

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
        openFile.setOnAction(event -> openFile());
        saveFile.setOnAction(event -> saveFile(stage));
        endEdit.setOnAction(event -> endEdit());
        eraser.setOnAction(event -> modeChange());
        allDelete.setOnAction(event -> fieldDelete());
        fileMenu.getItems().addAll(makeNewFile, openFile, saveFile, endEdit);
        editMenu.getItems().addAll(allDelete, eraser);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        GridPane gridPane = new GridPane();
        initMapChips();
        initMapField(gridPane);

        VBox root = new VBox();
        root.getChildren().addAll(menuBar, gridPane);
        Scene scene = new Scene(root);
        scene.setOnMouseDragged(event -> mouseOnAction(event));
        stage.setScene(scene);
        stage.show();
    }
    private void initMapChips() {
        mapChips[0] = new Image("mapChip\\0.bmp");
        mapChips[1] = new Image("mapChip\\1.bmp");
        mapChips[2] = new Image("mapChip\\1.bmp");
        mapChips[3] = new Image("mapChip\\1.bmp");
        mapChips[4] = new Image("mapChip\\1.bmp");
        mapChips[5] = new Image("mapChip\\1.bmp");
        mapChips[6] = new Image("mapChip\\1.bmp");
        mapChips[7] = new Image("mapChip\\1.bmp");
        mapChips[8] = new Image("mapChip\\1.bmp");
    }
    private void initMapField(GridPane gridPane) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
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
        try {
            mapField[x][y].setImage(mapChips[1]);
            mapField[x][y].setFieldNumber(1);
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
    private void newFile(){}
    private void openFile(){}
    /* ファイル保存用の処理 */
    private void saveFile(Stage stage){
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("map形式", "*.map"),
            new FileChooser.ExtensionFilter("すべてのファイル", "*.*")
        );
        outputToFile(fc.showSaveDialog(stage), mapInfoToString());
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
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    private String mapInfoToString() {
    /* mapinfoの要素をStringに変換するメソッド */
        String mapData = "";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                buf.append(String.valueOf(mapField[j][i].getFieldNumber()));
            }
            buf.append("\n");
        }
        mapData = buf.toString();
        return mapData;       
    }

    private void endEdit(){}
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mapField[i][j].setImage(mapChips[0]);
                mapField[i][j].setFieldNumber(0);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}