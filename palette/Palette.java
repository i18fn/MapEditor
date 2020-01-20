package palette;

import mapfield.MapChip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Palette {
    private static Palette palette = new Palette();
    private int nowChipNumber = 0;
    private int paletteLength = 16;
    private Image[] mapChips = new Image[paletteLength];
    public ImageView[] paletteIv = new ImageView[paletteLength];
    private Image nowChip = mapChips[nowChipNumber];
    public ImageView nowView = new ImageView(nowChip);
    private Palette() {}
    public static Palette getPalette() {
        return palette;
    }
    public Image getMapChips(int chipNumber) {
        return mapChips[chipNumber];
    }
    public int getPaletteLength() {
        return paletteLength;
    }
    public void initPalette(String imageUrl, int chipNumber) {
        int size = 52;
        final int I = chipNumber;
        paletteIv[I] = new MapChip(new Image(imageUrl, size, size, true, false), I);
        paletteIv[I].setOnMouseClicked(event -> chipChange(I));
        mapChips[I] = new Image(imageUrl);
    }
    public void chipChange(int chip) {
        nowChipNumber = chip;
        nowChip = mapChips[nowChipNumber];
        nowView.setImage(mapChips[chip]);
    }
    public int getNowChipNumber() {
        return nowChipNumber;
    }
    public Image getNowImage() {
        return nowChip;
    }
    public Image getImage(int chipNumber) {
        return mapChips[nowChipNumber];
    }
}