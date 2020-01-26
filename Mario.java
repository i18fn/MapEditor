import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class Mario extends ImageView{
    private ImageView iv;
    private boolean onFloor;
    private int vy; 
    public Mario(String url, int x, int y) {
        this.iv = new ImageView(url);
        this.iv.setX(x);
        this.iv.setY(y);
        this.onFloor = true;
    }
    public ImageView getImageView() {
        return this.iv;
    }
    public void move(KeyEvent e, Timeline tl) {
        double x = this.iv.getLayoutX();
        switch (e.getCode()) {
            case RIGHT:
                this.iv.setLayoutX(x + 10);
                break;
            case LEFT:
                this.iv.setLayoutX(x - 10);
                break;
            case UP:
                tl.play();
                break;
            default:
                return;
        }
    }
    public void jump() {
        double y = this.iv.getLayoutY();
        if (this.onFloor) {
            vy = -20;
            double newy = (double)(vy + y);
            this.iv.setLayoutY(newy);
            this.onFloor = false;
        } else {
            vy += 1;
            this.iv.setLayoutY(y + vy);
        }
        System.out.println("vy  = " + vy);
    }
}