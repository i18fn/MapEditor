package canvas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import palette.MapChip;
/**
 * Canvas内の画素(pixel)を表現するクラス.
 * 
 * @author i18fn
 */
class Pixel extends ImageView {
    private int chipNumber;

    public Pixel(Image image) {
        super(image);
        chipNumber = 0;
    }

    public void change(MapChip mapChip) {
        this.chipNumber = mapChip.chipNumber;
        setImage(mapChip.chipImage);
    }

    public int getChipNumber() {
        return chipNumber;
    }
}
