package command;

import mapfield.*;

public class saveFieldCommand implements Command {
    private int[][] oldCanvas;
    private Canvas canvas;
    public saveFieldCommand(Canvas canvas) {
        oldCanvas = new int[canvas.getWidth()][canvas.getHeight()];
        for (int i = 0; i < oldCanvas[0].length; i++) {
            for (int j = 0; j < oldCanvas.length; j++) {
                oldCanvas[j][i] = canvas.mapField[j][i].getFieldNumber();
            }
        }
        this.canvas = canvas;
    }
    public void execute() {}
    public void undo() {
        for (int i = 0; i < oldCanvas[0].length; i++) {
            for (int j = 0; j < oldCanvas.length; j++) {
                canvas.setChip(oldCanvas[j][i], j, i);
            }
        }
    }
}