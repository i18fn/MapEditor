package command;

public interface Command {
    // コマンドを表すインターフェース
    public abstract void execute();
    public abstract void undo();
}