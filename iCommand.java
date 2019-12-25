public interface iCommand {
    public abstract void action();
    public abstract void undo();
    public abstract void redo();
}