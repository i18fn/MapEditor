package command;

import java.util.ArrayList;

public class MacroCommand implements Command {
    private ArrayList<Command> commands;
    public MacroCommand() {
        commands = new ArrayList<>();
    }
    public void add(Command c) {
        commands.add(c);
    }
    public void execute() {

    }
}