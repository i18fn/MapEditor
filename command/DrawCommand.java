package command;

import mapfield.*;
import javafx.scene.input.MouseEvent;

public class DrawCommand implements Command {
    // 描画を表すクラス(必要性があるか不明)
    private Canvas canvas;
    private int x;
    private int y;
    private boolean saveFlag;
    public DrawCommand(Canvas canvas, int x, int y) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
    }
    public DrawCommand(Canvas canvas, MouseEvent event){
        this.canvas = canvas;
        this.x = (int)event.getX() / 32;
        this.y = (int)event.getY() / 32;
    }
    public void execute() {
        if (x >= canvas.getWidth()| y >= canvas.getHeight()) {
            saveFlag = false;
            return;
        }
        try {
            canvas.setChip(x, y);
            saveFlag = true;
        } catch (ArrayIndexOutOfBoundsException e) {
            saveFlag = false;
            return;
        }
    }
    public void undo() {}
    public boolean getFlag() {
        return saveFlag;
    }
}