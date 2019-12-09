import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class MapChip extends ImageView {
    private byte fieldNumber;
    MapChip(){
        super();
    }
    MapChip(String url, byte fieldNumber){
        this(new Image(url), fieldNumber);
    }
    MapChip(Image image, byte fieldNumber){
        super(image);
        this.fieldNumber = fieldNumber;
    }
} 