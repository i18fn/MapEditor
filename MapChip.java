import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MapChip extends ImageView {
    private int fieldNumber;
    MapChip(Image image) {
        this(image, 0);
    }
    MapChip(Image image, int fieldNumber) {
        super(image);
        this.fieldNumber = fieldNumber;
    }
    int getFieldNumber() {
        return this.fieldNumber;
    }
    void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
} 