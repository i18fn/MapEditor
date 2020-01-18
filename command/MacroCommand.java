package command;

import java.util.Stack;
import java.util.Iterator;

public class MacroCommand implements Command {
    protected Stack<Command> commands;
    public MacroCommand() {
        commands = new Stack<>();
    }
    public void add(Command c) {
        commands.push(c);
    }
    public void execute() {
        Iterator<Command> it = commands.iterator();
        while (it.hasNext()) {
            ((Command)it.next()).execute();
        }
    }
    public void undo() {
        if (!commands.empty()) {
            commands.pop().undo();
        }
    }
    public void clear() {
        commands.clear();
    }
}