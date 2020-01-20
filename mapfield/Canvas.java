package mapfield;

import palette.*;

public class Canvas {
    private Palette palette = Palette.getPalette();
    public final int WIDTH_MAX = 46;
    public final int HEIGHT_MAX = 28;
    private int width = WIDTH_MAX;
    private int height = HEIGHT_MAX;
    public MapChip[][] mapField = new MapChip[WIDTH_MAX][HEIGHT_MAX];
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public void sizeChange(int width, int height) {
        reset();
        this.width = width;
        this.height = height;
        fieldHiding();
    }
    private void fieldHiding() {
        for (int i = 0; i < WIDTH_MAX; i++) {
            for (int j = 0; j < HEIGHT_MAX; j++) {
                if (i >= width || j >= height) {
                    mapField[i][j].setImage(null);
                } else {
                    mapField[i][j].setImage(palette.getMapChips(0));
                }
            }
        }
    }
    public void setChip(int x, int y) {
        setChip(palette.getNowChipNumber(), x, y);
    }
    public void setChip(int chipNumber, int x, int y) { 
        palette.chipChange(chipNumber);
        mapField[x][y].setImage(palette.getMapChips(chipNumber));
        mapField[x][y].setFieldNumber(chipNumber);
    }
    public int[][] getMapDataInt() {
        int[][] data = new int[this.width][this.height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = mapField[i][j].getFieldNumber();
            }
        }
        return data;
    }
    public void reset() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                setChip(0, i, j);
            }
        }
    }
}