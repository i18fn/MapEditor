import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import palette.*;

public class TestPlay {
    private static int width;
    private static int height;
    private static ImageView[][] map;
    public static void start(Stage stage, int[][] mapData) {
        width = mapData.length;
        height = mapData[0].length;
        map = new ImageView[width][height];
        stage.setTitle("テストプレイ");
        stage.setWidth(width * 32);
        stage.setHeight(height * 32);
        GridPane mapPane = new GridPane();
        loadMap(mapData, mapPane);
        stage.setScene(new Scene(mapPane));
        stage.show();
    }
    private static void loadMap(int[][] mapData, GridPane mapPane) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int chip = mapData[i][j];
                System.out.println(chip);
                if (chip == 0) {
                    map[i][j] = new ImageView(new Image("mapChip\\none.bmp"));
                } else {
                    map[i][j] = new ImageView(new Image("mapChip\\" + String.valueOf(chip) + ".bmp"));
                    mapPane.add(map[i][j], i, j);
                }
                mapPane.add(map[i][j], i, j);
            }
        }
    }
}