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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
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
    private History history;

    public void start(Stage stage) throws Exception {
        saveFlag = false;
        // trueなら終了時保存するかどうかを聞く
        mouseMode = false;
        // trueなら描画中
        canvas = new Canvas();
        palette = Palette.getPalette();
        history = new History(canvas);
        
        stage.setTitle("マップエディタ");
        stage.setWidth(1698);
        stage.setHeight(1024);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("ファイル");
        Menu editMenu = new Menu("編集");
        MenuItem newFileiItem = new MenuItem("新規作成");
        MenuItem openFileiItem = new MenuItem("開く");
        MenuItem saveFileiItem = new MenuItem("保存");
        MenuItem endEditItem = new MenuItem("終了");
        MenuItem allDeleteItem = new MenuItem("全消去");
        MenuItem undoItem = new MenuItem("元に戻す");
        MenuItem redoItem = new MenuItem("やり直し");
        newFileiItem.setOnAction(event -> makeNewFile());
        openFileiItem.setOnAction(event -> openFile());
        saveFileiItem.setOnAction(event -> saveFile());
        endEditItem.setOnAction(event -> endEdit(stage, event));
        allDeleteItem.setOnAction(event -> fieldAllDelete());
        fileMenu.getItems().addAll(newFileiItem, openFileiItem, saveFileiItem, endEditItem);
        editMenu.getItems().addAll(undoItem, redoItem, allDeleteItem);
        menuBar.getMenus().addAll(fileMenu, editMenu);
        newFileiItem.setAccelerator(KeyCombination.valueOf("Shortcut+n"));
        openFileiItem.setAccelerator(KeyCombination.valueOf("Shortcut+o"));
        undoItem.setAccelerator(KeyCombination.valueOf("Shortcut+z"));
        redoItem.setAccelerator(KeyCombination.valueOf("Shortcut+y"));

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
        nowChipPane.getChildren().addAll(lblNowChip, palette.nowView);

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
        ButtonX btnNew = new ButtonX(32, 32, "btnNewFile.png");
        ButtonX btnOpen = new ButtonX(32, 32, "btnOpenFile.png");
        ButtonX btnSave = new ButtonX(32, 32, "btnSave.png");
        ButtonX btnDelete = new ButtonX(32, 32, "btnAllDelete.png");
        ButtonX btnUndo = new ButtonX(32, 32, "btnUndo.png");
        ButtonX btnRedo = new ButtonX(32, 32, "btnRedo.png");
        ButtonX btnPlay = new ButtonX(32, 32, "btnGamePlay.png");
        btnNew.setOnAction(event -> makeNewFile());
        btnOpen.setOnAction(event -> openFile());
        btnSave.setOnAction(event -> saveFile());
        btnDelete.setOnAction(event -> fieldAllDelete());
        btnUndo.setOnAction(event -> undo());
        btnRedo.setOnAction(event -> redo());
        buttonBar.getButtons().addAll(btnNew, btnOpen, btnSave, btnDelete, btnUndo, btnRedo, btnPlay);
    }
    private void draw(MouseEvent event) {   
        if (!mouseMode) {
            // 描画中でなければ、(!mouseModeなら) 変更前の盤面を保存する
            history.add(new SaveFieldCommand(canvas));
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
    private void redo() {
        history.redo();
    }
    private void openFile() {
        OpenFile of = new OpenFile();
        if (of.openFile(canvas) == 1) {
            history.clear();
        }
    }
    private void saveFile() {
        SaveFile sf = new SaveFile();
        sf.saveFile(canvas);
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
        history.add(new SaveFieldCommand(canvas));
        canvas.reset();
    }
    private void makeNewFile() {
        askSizeStageShow();
    }
    private void askSizeStageShow() {
        Stage askSizeStage = new Stage();
        Label lblWidth = new Label(" 　幅");
        Label lblHeight = new Label("　高さ");
        Label lblAsk = new Label("作成するマップの大きさ");
        lblWidth.setMinWidth(50);
        lblHeight.setMinWidth(50);
        lblAsk.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        Spinner<Integer> spinnerWidth = new Spinner<>();
        Spinner<Integer> spinnerHeight = new Spinner<>();
        spinnerWidth.setEditable(true);
        spinnerHeight.setEditable(true);
        spinnerWidth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, canvas.WIDTH_MAX, canvas.WIDTH_MAX));
        spinnerHeight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, canvas.HEIGHT_MAX, canvas.HEIGHT_MAX));
        spinnerWidth.setMaxWidth(120);
        spinnerHeight.setMaxWidth(120);
        spinnerWidth.setMaxHeight(40);
        spinnerHeight.setMaxHeight(40);
        Button btnOK = new Button("OK");
        Button btnCancel = new Button("キャンセル");
        btnOK.setMinWidth(40);
        btnCancel.setMinWidth(40);
        btnOK.setOnAction(event -> okBtnAction(askSizeStage, spinnerWidth.getValue(), spinnerHeight.getValue()));
        btnCancel.setOnAction(event -> askSizeStage.close());
        HBox btnHBox = new HBox();
        HBox widthHBox = new HBox();
        HBox heightHBox = new HBox();
        btnHBox.getChildren().addAll(btnOK, btnCancel);
        widthHBox.getChildren().addAll(lblWidth, spinnerWidth);
        widthHBox.setAlignment(Pos.CENTER);
        heightHBox.getChildren().addAll(lblHeight, spinnerHeight);
        heightHBox.setAlignment(Pos.CENTER);
        VBox root = new VBox();
        root.getChildren().addAll(lblAsk, widthHBox, heightHBox, btnHBox);
        askSizeStage.setScene(new Scene(root));
        askSizeStage.show();        
    }
    private void okBtnAction(Stage stage, int width, int height) {
        canvas.sizeChange(width, height);
        stage.close();
    }
    private void testPlay() {
        canvas.getMapDataInt();
    }
    public static void main(String[] args) {
        launch(args);
    }
}