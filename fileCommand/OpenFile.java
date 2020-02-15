package filecommand;

import editorlib.*;
import mapfield.Canvas;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class OpenFile {
    public int openFile(Canvas canvas) {
        FileChooser fc = new FileChooser();
        fc.setTitle("ファイル選択");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
        try {
            inputToFile(fc.showOpenDialog(null), canvas);
        } catch(NullPointerException e) {
            return -1;
        }
        return 1;
    }
    private void inputToFile(File file, Canvas canvas) {
        String data;
        int[][] mapinfo;
        try {
            BufferedReader fRead;
            fRead = new BufferedReader(new FileReader(file));
            // 1,2行目のデータの幅と高さの値を読み込む
            int mapWidth = Integer.parseInt(fRead.readLine());
            int mapHeight = Integer.parseInt(fRead.readLine());
            canvas.sizeChange(mapWidth, mapHeight);
            mapinfo = new int[mapWidth][mapHeight];
            int i = 0;
            while ((data = fRead.readLine()) != null) {
                splitInt(data, mapinfo, i);
                i++;
            }
            fRead.close();
            for (int j = 0; j < mapHeight; j++) {
                for (int k = 0; k < mapWidth; k++) {
                    canvas.setChip(mapinfo[k][j], k, j);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void splitInt(String strIndex, int[][] intIndex, int row) {
        // 一行ずつ読み込み、String.split()メソッドで一文字ごとに分割して
        // 16進数(String)を10進数(int)に変換する
        String[] str = strIndex.split("");
        for (int i = 0; i < str.length; i++) {
            intIndex[i][row] = Hex.HexToDeci(str[i].charAt(0));
        }
    }
}