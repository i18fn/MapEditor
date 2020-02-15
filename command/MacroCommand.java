package command;

import java.util.Stack;

public class MacroCommand implements Command {
    // コマンドの集合体を表すクラス
    protected Stack<Command> commands;
    public MacroCommand() {
        commands = new Stack<>();
    }
    public void add(Command c) {
        commands.push(c);
    }
    public void execute() {}
    public void undo() {
        if (!commands.empty()) {
            commands.pop().undo();
        }
    }
    public void clear() {
        commands.clear();
    }
}