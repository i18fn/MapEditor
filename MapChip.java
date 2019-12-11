import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MapChip extends ImageView {
    private int fieldNumber;
    MapChip() {
        super();
    }
    MapChip(Image image) {
        this(image, 0);
    }
    MapChip(String url) {
        this(new Image(url), 0);
    }
    MapChip(String url, int fieldNumber) {
        this(new Image(url), fieldNumber);
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