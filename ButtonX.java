import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonX extends Button {
    public ButtonX() {
        super();
    }
    public ButtonX(double width, double height) {
        super();
        setPrefWidth(width);
        setPrefHeight(height);
    }
    public ButtonX(double width, double height, String imageUrl) {
        super();
        setPrefWidth(width);
        setPrefHeight(height);
        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(imageUrl))));
    }
} 