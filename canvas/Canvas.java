package canvas;

import canvas.Pixel;
import palette.MapChip;

/**
 * Canvasを表現するクラス.
 * マップのサイズ分の大きさのPixelの配列を持つ.
 * 
 * @author i18fn
 */
public class Canvas {
    public final int WIDTH_MAX = 255;
    public final int HEIGHT_MAX = 255;
    private int width;
    private int height;
    private Pixel[][] pixels;

    public Canvas(MapChip mapChip) {
        this(40, 30, mapChip);
    }

    public Canvas(int width, int height, MapChip mapChip) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[width][height];
        reset(mapChip);
    }

    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public void draw(int posX, int posY, MapChip mapChip) {
        pixels[posX][posY].change(mapChip);
    }

    public void reset(MapChip mapChip) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                draw(i, j, mapChip);
            }
        }
    }
}
