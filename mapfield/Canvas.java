package mapfield;

public class Canvas {
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
}