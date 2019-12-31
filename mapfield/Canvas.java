package mapfield;

import palette.*;

public class Canvas {
    private Palette palette = Palette.getPalette();
    public final int WIDTH_MAX = 46;
    public final int HEIGHT_MAX = 29;
    private int width = WIDTH_MAX;
    private int height = HEIGHT_MAX;
    public MapChip[][] mapField = new MapChip[WIDTH_MAX][HEIGHT_MAX];
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void sizeChange(int width, int height) {
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
    public void setChip(int chipNumber, int x, int y) {
        mapField[x][y].setImage(palette.getMapChips(chipNumber));
    }
}