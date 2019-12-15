    // /* ファイル保存用の処理 */
    // private void saveFile(Stage stage){
    //     FileChooser fc = new FileChooser();
    //     fc.setTitle("ファイル選択");
    //     fc.setInitialDirectory(new File(System.getProperty("user.home")));
    //     fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
    //     outputToFile(fc.showSaveDialog(null), mapInfoToString());
    //     saveFlag = false;
    // }
    // private void outputToFile(File file, String str) {
    //     try {
    //         if (file != null) {
    //             if (file.exists() == false) {
    //                 file.createNewFile();
    //             }
    //             FileWriter fileWriter = new FileWriter(file);
    //             fileWriter.write(str);
    //             fileWriter.close();
    //         } else {
    //             return;
    //         }
    //     } catch (IOException e) {
    //         System.out.println(e);
    //     }
    // }
    // private String mapInfoToString() {
    // /* mapinfoの要素をStringに変換するメソッド */
    //     String mapData = "";
    //     StringBuilder buf = new StringBuilder();
    //     for (int i = 0; i < COLUMN_MAX; i++) {
    //         for (int j = 0; j < ROW_MAX; j++) {
    //             buf.append(String.valueOf(mapField[j][i].getFieldNumber()));
    //         }
    //         buf.append("\n");
    //     }
    //     mapData = buf.toString();
    //     return mapData;       
    // }