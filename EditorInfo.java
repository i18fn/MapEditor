// import javafx.scene.image.ImageView;
// import javafx.scene.layout.GridPane;
// import javafx.scene.image.Image;
// import java.util.ArrayDeque;
// import java.util.Deque;
// import java.util.NoSuchElementException;

// class EditorInfo {
//     int nowChipNumber = 1;
//     int[][] fieldInfo;
//     MapChip[][] mapField = new MapChip[ROW_MAX][COLUMN_MAX];
//     /* パレット(マップチップ)の情報 */
//     Image[] mapChips = new Image[16];
//     ImageView[] palette = new ImageView[16];
//     ImageView nowChip = new ImageView(mapChips[1]);

//     EditorInfo(int nowChipNumber) {
//         this.nowChipNumber = nowChipNumber;
//         fieldInfo = new int[row][column];
//     }
//     private void chipChange(int chip) {
//         nowChipNumber = chip;
//         nowChip.setImage(mapChips[chip]);
//     }
//     void fieldAllDelete() {
//         for (int i = 0; i < ROW_MAX; i++) {
//             for (int j = 0; j < COLUMN_MAX; j++) {
//                 mapField[i][j].setImage(mapChips[0]);
//                 mapField[i][j].setFieldNumber(0);
//             }
//         }
//     }
//     int draw(int x, int y) {
//         if (x >= row || y >= column) return -1;
//         try {
//             mapField[x][y].setImage(mapChips[nowChipNumber]);
//             mapField[x][y].setFieldNumber(nowChipNumber);
//             return 1;
//         } catch (ArrayIndexOutOfBoundsException e) {
//             return -1;
//         }
//     }
// }