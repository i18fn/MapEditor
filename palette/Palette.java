package palette;

import palette.*;

public class Palette {
    private MapChip[] mapChips;
    private int nowChipNumber;

    public Palette() {
        mapChips = new MapChip[16];
    }

    public MapChip getNowMapChip() {
        return mapChips[nowChipNumber];
    }

    public void chipChange(int chipNumber) {
        this.nowChipNumber = chipNumber;
    }
}