import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

class EditorInfo {
    final int ROW_MAX = 46;
    final int COLUMN_MAX = 29;
    int row = ROW_MAX;
    int column = COLUMN_MAX;
    int nowChipNumber = 1;
    int[][] fieldInfo;
    MapChip[][] mapField = new MapChip[ROW_MAX][COLUMN_MAX];
    /* パレット(マップチップ)の情報 */
    Image[] mapChips = new Image[16];
    ImageView[] palette = new ImageView[16];
    ImageView nowChip = new ImageView(mapChips[1]);

    EditorInfo(int nowChipNumber) {
        this.nowChipNumber = nowChipNumber;
        fieldInfo = new int[row][column];
    }
    void init(GridPane palettePane, GridPane fieldPane) {
        initMapChips();
        initPalette(palettePane);
        initMapField(fieldPane);
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
            mapChips[i] = new Image(url);
        }
    }
    private void initMapField(GridPane fieldPane) {
        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COLUMN_MAX; j++) {
                mapField[i][j] = new MapChip(mapChips[0], 0);
                GridPane.setConstraints(mapField[i][j], i, j);
                fieldPane.getChildren().addAll(mapField[i][j]);
            }
        }
    }
    private void chipChange(int chip) {
        nowChipNumber = chip;
        nowChip.setImage(mapChips[chip]);
    }
    void fieldAllDelete() {
        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COLUMN_MAX; j++) {
                mapField[i][j].setImage(mapChips[0]);
                mapField[i][j].setFieldNumber(0);
            }
        }
    }
    void sizeChange(int width, int height) {
        this.row = width;
        this.column = height;
        fieldHiding();
    }
    private void fieldHiding() {
        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COLUMN_MAX; j++) {
                if (i > row || j > column) {
                    mapField[i][j].setImage(null);
                } else {
                    mapField[i][j].setImage(mapChips[0]);
                }
            }
        }
    }
    int draw(int x, int y) {
        if (x > row || y > column) return -1;
        try {
            mapField[x][y].setImage(mapChips[nowChipNumber]);
            mapField[x][y].setFieldNumber(nowChipNumber);
            return 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}