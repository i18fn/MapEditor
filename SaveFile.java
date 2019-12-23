import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public void saveFile(Stage stage, EditorInfo editorInfo) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        outputToFile(fc.showSaveDialog(stage), mapInfoToString(editorInfo.mapField, editorInfo.row, editorInfo.column), editorInfo); 
    }
    private void outputToFile(File file, String str, EditorInfo editorInfo) {
        try {
            if (file != null) {
                if (file.exists() == false) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(Integer.toString(editorInfo.row));
                fileWriter.write("\n");
                fileWriter.write(Integer.toString(editorInfo.column));
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
    private String mapInfoToString(MapChip[][] mapField, int row, int column) {
        String mapData = "";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                buf.append(String.valueOf(Hex.deciToHex(mapField[j][i].getFieldNumber())));
            }
            buf.append("\n");
        }
        mapData = buf.toString();
        return mapData;       
    }
}
