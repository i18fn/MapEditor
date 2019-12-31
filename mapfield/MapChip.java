package mapfield;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapChip extends ImageView {
    private int chipNumber;
    public MapChip(Image image) {
        this(image, 0);
    }
    public MapChip(Image image, int chipNumber) {
        super(image);
        this.chipNumber = chipNumber;
    }
    public int getFieldNumber() {
        return this.chipNumber;
    }    
}