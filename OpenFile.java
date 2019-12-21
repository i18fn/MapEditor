import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import javafx.scene.image.Image;

public class OpenFile {
    public void openFile(MapChip[][] mapField, Image[] mapChips) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        inputToFile(fc.showOpenDialog(null), mapField, mapChips);
    }
    private void inputToFile(File file, MapChip[][] mapField, Image[] mapChips) {
        String data;
        int[][] mapinfo;
        try {
            BufferedReader fRead;
            fRead = new BufferedReader(new FileReader(file));
            int mapWidth = Integer.parseInt(fRead.readLine());
            int mapHeight = Integer.parseInt(fRead.readLine());
            mapinfo = new int[mapWidth][mapHeight];
            int i = 0;
            while ((data = fRead.readLine()) != null) {
                splitInt(data, mapinfo, i);
                i++;
            }
            fRead.close();
            for (int j = 0; j < mapinfo[0].length; j++) {
                for (int k = 0; k < mapinfo.length; k++) {
                    mapField[k][j].setImage(mapChips[mapinfo[k][j]]);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private static void splitInt(String strIndex, int[][] intIndex, int row) {
        String[] str = strIndex.split("");
        for (int i = 0; i < str.length; i++) {
            intIndex[i][row] = Hex.HexToDeci(str[i].charAt(0));
        }
    }
}

