package command;

import mapfield.*;
import javafx.scene.input.MouseEvent;

public class DrawCommand implements Command {
    private Canvas canvas;
    private int x;
    private int y;
    public DrawCommand(Canvas canvas, MouseEvent event){
        this.canvas = canvas;
        x = (int)event.getX() / 32;
        y = (int)event.getY() / 32;
    }
    public void execute() {
        if (x >= canvas.getWidth()| y >= canvas.getHeight()) return;
        try {
            canvas.setChip(x, y);;
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }
}