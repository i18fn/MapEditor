package property;

/**
 * アプリの状態を管理するクラス
 * 
 * @author i18fn
 */
public class Property {
    private static Property singleton = new Property();
    
    public boolean saveFlag;
    public boolean mouseMode;

    private Property() {
        this.saveFlag = false;
        this.mouseMode = false;
    }

    public static Property getProperty() {
        return singleton;
    }
}
