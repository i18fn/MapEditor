    // /* ファイルオープン用の処理 */
    // private void openFile(Stage stage) {
    //     FileChooser fc = new FileChooser();
    //     fc.setTitle("ファイル選択");
    //     fc.setInitialDirectory(new File(System.getProperty("user.home")));
    //     fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("map形式", "*.map"));
    //     inputToFile(fc.showOpenDialog(null));
    // }
    // private void inputToFile(File file) {
    //     try {
    //         if (file != null) {
    //             FileReader fileReader = new FileReader(file);
    //             int data;
    //             int row = 0;
    //             int column = 0;
    //             while ((data = fileReader.read()) != -1) {
    //                 if (data == '\n') {
    //                     column++;
    //                     row = 0;
    //                 } else {
    //                     data = data & 0x0F;
    //                     mapField[row][column].setImage(mapChips[data]);
    //                     mapField[row][column].setFieldNumber(data);
    //                     row++;                        
    //                 }
    //             }
    //             fileReader.close();
    //         }
    //     } catch (IOException e) {
    //         System.out.println(e);
    //     }
    // }