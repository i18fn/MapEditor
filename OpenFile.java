import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class OpenFile {
    public void openFile(Stage stage, EditorInfo editorInfo) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        try {
            inputToFile(fc.showOpenDialog(stage), editorInfo);
        } catch(NullPointerException e) {
            System.out.println("ファイルを指定しなかった。");
        }
    }
    private void inputToFile(File file, EditorInfo editorInfo) {
        String data;
        int[][] mapinfo;
        try {
            BufferedReader fRead;
            fRead = new BufferedReader(new FileReader(file));
            int mapWidth = Integer.parseInt(fRead.readLine());
            int mapHeight = Integer.parseInt(fRead.readLine());
            editorInfo.sizeChange(mapWidth, mapHeight);
            mapinfo = new int[mapWidth][mapHeight];
            int i = 0;
            while ((data = fRead.readLine()) != null) {
                splitInt(data, mapinfo, i);
                i++;
            }
            fRead.close();
            for (int j = 0; j < mapHeight; j++) {
                for (int k = 0; k < mapWidth; k++) {
                    editorInfo.mapField[k][j].setImage(editorInfo.mapChips[mapinfo[k][j]]);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void splitInt(String strIndex, int[][] intIndex, int row) {
        String[] str = strIndex.split("");
        for (int i = 0; i < str.length; i++) {
            intIndex[i][row] = Hex.HexToDeci(str[i].charAt(0));
        }
    }
}