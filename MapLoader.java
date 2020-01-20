import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MapLoader {
    private static char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static int[][] mapLoad(String filename){
        String data;
        int[][] mapfield;
        try {
            BufferedReader fRead;
            fRead = new BufferedReader(new FileReader(new File(filename)));
            int mapWidth = Integer.parseInt(fRead.readLine());
            int mapHeight = Integer.parseInt(fRead.readLine());
            mapfield = new int[mapWidth][mapHeight];
            int i = 0;
            while ((data = fRead.readLine()) != null) {
                splitInt(data, mapfield, i);
                i++;
            }
            fRead.close();
            return mapfield;
        } catch (IOException e) {
            System.out.println(filename + " IS NOT FOUND");
        }
        int[][] temp = {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        return temp;
    }
    private static void splitInt(String strIndex, int[][] intIndex, int row) {
        String[] str = strIndex.split("");
        for (int i = 0; i < str.length; i++) {
            intIndex[i][row] = HexToDeci(str[i].charAt(0));
        }
    }
    private static int HexToDeci(char hex) {
        int i = 0;
        while (i < hexChar.length){
            if (hexChar[i] == hex) {
                break;
            }
            i++;
        }
        if (i == hexChar.length) {
            return -1;
        }
        return i;
    }
}