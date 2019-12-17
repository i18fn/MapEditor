import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.image.Image;

public class OpenFile {

    public OpenFile() {}

    public void openFile(MapChip[][] mapField, Image[] mapChips) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        inputToFile(fc.showOpenDialog(null), mapField, mapChips);
    }

    private void inputToFile(File file, MapChip[][] mapField, Image[] mapChips) {
        try {
            if (file != null) {
                FileReader fileReader = new FileReader(file);
                char hex;
                int row = 0;
                int column = 0;
                int data;
                while ((data = fileReader.read()) != -1) {
                    hex = (char)data;
                    if (hex == '\n') {
                        column++;
                        row = 0;
                    } else {
                        data = Hex.HexToDeci(hex);
                        mapField[row][column].setImage(mapChips[data]);
                        mapField[row][column].setFieldNumber(data);
                        row++;                        
                    }
                }
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}

