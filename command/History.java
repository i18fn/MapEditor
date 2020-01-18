package command;

import java.util.Stack;
import mapfield.*;

public class History extends MacroCommand {
    private Stack<Command> redoStack;
    private Canvas canvas;
    public History(Canvas canvas) {
        super();
        redoStack = new Stack<>();
        this.canvas = canvas;
    }
    public void undo() {
        if (!commands.empty()) {
            redoStack.push(new SaveFieldCommand(this.canvas));
            Command c = commands.pop();
            c.undo();
        }
    }
    public void redo() {
        if (!redoStack.empty()) {
            Command c = redoStack.pop();
            c.undo();
            add(c);
        }
    }
}