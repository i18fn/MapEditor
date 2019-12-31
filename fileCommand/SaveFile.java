package filecommand;

import editorlib.*;
import mapfield.Canvas;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public void saveFile(Canvas canvas) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        outputToFile(fc.showSaveDialog(null), canvas); 
    }
    private void outputToFile(File file, Canvas canvas) {
        String str = mapInfoToString(canvas);
        try {
            if (file != null) {
                if (file.exists() == false) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(Integer.toString(canvas.getWidth()));
                fileWriter.write("\n");
                fileWriter.write(Integer.toString(canvas.getHeight()));
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
    private String mapInfoToString(Canvas canvas) {
        String mapData = "";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < canvas.getHeight(); i++) {
            for (int j = 0; j < canvas.getWidth(); j++) {
                buf.append(String.valueOf(Hex.deciToHex(canvas.mapField[j][i].getFieldNumber())));
            }
            buf.append("\n");
        }
        mapData = buf.toString();
        return mapData;       
    }
}
