import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestPlay {
    private int width;
    private int height;
    private ImageView[][] map;
    private ImageView marioImage;
    private Timeline timeline;
    private boolean onFloor;
    private int vy; 
    public TestPlay(Stage stage, int[][] mapData) {
        start(stage, mapData);
    }
    public void start(Stage stage, int[][] mapData) {
        width = mapData.length;
        height = mapData[0].length;
        stage.setTitle("テストプレイ");
        stage.setWidth(width * 32 + 16);
        stage.setHeight(height * 32 + 16);
        onFloor = false;
        map = new ImageView[width][height];
        marioImage = new ImageView(new Image("mario.bmp"));
        marioImage.setX(320);
        marioImage.setY(320);
        Group map = new Group();
        loadMap(mapData, map);
        Group root = new Group();
        root.getChildren().addAll(map, marioImage);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> move(event));
        stage.setOnCloseRequest(event -> exit());
        stage.setScene(scene);
        stage.show();

        timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> freeFall()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void exit() {
        timeline.stop(); 
    }
    private void freeFall() {
        int y = (int)marioImage.getY();
        onFloor = collisionY(y + vy);
        if (onFloor) {
            vy += 1;
            marioImage.setY(y + vy);
        } else {
            vy = 1;
        }
        // int y = (int)marioImage.getLayoutY();
        // if (!onFloor) {
        //     vy += 1;
        // }
        // onFloor = collisionY(y + vy);
        // if (onFloor) {
           
        // }
    }
    public void move(KeyEvent e) {
        double x = marioImage.getX();
        switch (e.getCode()) {
            case RIGHT:
                marioImage.setX(x + 8);
                break;
            case LEFT:
                marioImage.setX(x - 8);
                break;
            case UP:
                break;
            default:
                return;
        }
    }
    public void jump() {
    }
    private void collisionX(){}
    private boolean collisionY(int newY){
        int x = (int)marioImage.getX();
        System.out.println("x:" + x + "  y:" + newY);
        try {
            if (map[x/32][newY/32 + 1].isManaged()) {
                // 床が無い場合
                return false;
            } else {
                // 床が有る場合
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }
    private void loadMap(int[][] mapData, Group g) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int chip = mapData[i][j];
                if (chip == 0) {
                    map[i][j] = new ImageView(new Image("mapChip\\white.bmp"));
                    map[i][j].setManaged(false);
                } else {
                    map[i][j] = new ImageView(new Image("mapChip\\" + String.valueOf(chip) + ".bmp"));
                }
                map[i][j].setX(32 * i);
                map[i][j].setY(32 * j);
                g.getChildren().add(map[i][j]);
            }
        }
    }
}