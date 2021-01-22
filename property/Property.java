package property;

public class Property {
    private static Property singleton = new Property();
    
    public boolean saveFlag;
    public boolean mouseMode;
    public int mapchipNumber;

    private Property() {
        this.saveFlag = false;
        this.mouseMode = false;
        this.mapchipNumber = 0;
    }

    public static Property getInstance() {
        return singleton;
    }
}
