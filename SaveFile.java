import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public void saveFile(MapChip[][] mapField){
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        outputToFile(fc.showSaveDialog(null), mapInfoToString(mapField), mapField);
    }
    private void outputToFile(File file, String str, MapChip[][] mapField) {
        try {
            if (file != null) {
                if (file.exists() == false) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(Integer.toString(mapField.length));
                fileWriter.write("\n");
                fileWriter.write(Integer.toString(mapField[0].length));
                fileWriter.write("\n");
                fileWriter.write(str);
                fileWriter.close();
            } else {
                return;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private String mapInfoToString(MapChip[][] mapField) {
        String mapData = "";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < mapField[0].length; i++) {
            for (int j = 0; j < mapField.length; j++) {
                buf.append(String.valueOf(Hex.deciToHex(mapField[j][i].getFieldNumber())));
            }
            buf.append("\n");
        }
        mapData = buf.toString();
        return mapData;       
    }
}
