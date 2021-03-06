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
        // 重力によるy軸方向の移動
        int y = (int)marioImage.getY();
        onFloor = collisionY(y + vy);
        if (onFloor) {
            vy += 1;
            marioImage.setY(y + vy);
        } else {
            vy = 1;
        }
    }
    public void move(KeyEvent e) {
        int x = (int)marioImage.getX();
        switch (e.getCode()) {
            case RIGHT:
                if (collisionX(x + 8)) {
                    marioImage.setX(x + 8);
                }
                break;
            case LEFT:
                if (collisionX(x - 8)) {
                    marioImage.setX(x - 8);
                }
                break;
            case UP:
                if (!onFloor) {
                    vy = -24;
                }
                break;
            default:
                return;
        }
    }
    private boolean collisionX(int newX){
        // x方向の衝突判定
        int y = (int)marioImage.getY();
        try {
            if (map[newX/32 + 1][y/32].isManaged() || map[newX/32 ][y/32].isManaged()) {
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
    private boolean collisionY(int newY){
        // y方向の衝突判定
        int x = (int)marioImage.getX();
        try {
            if (map[x/32][newY/32 + 1].isManaged() || map[x/32][newY/32].isManaged()) {
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