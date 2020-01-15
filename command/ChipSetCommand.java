package command;

import mapfield.*;

public class ChipSetCommand implements Command {
    private int[][] canvas;
    public ChipSetCommand(Canvas canvas) {
        this.canvas = new int[canvas.getWidth()][canvas.getHeight()];
        for (int i = 0; i < this.canvas[0].length; i++) {
            for (int j = 0; j < this.canvas.length; j++) {
                this.canvas[j][i] = canvas.mapField[j][i].getFieldNumber();
            }
        }
    }
    public void execute() {

    }
}