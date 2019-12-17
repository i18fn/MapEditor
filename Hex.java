public class Hex {
    private static char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static String deciToHex(int deci) {
        return String.valueOf(hexChar[deci]);
    }
    
    public static int HexToDeci(char hex) {
        int i = 0;
        while (i < hexChar.length){
            if (hexChar[i] == hex) {
                break;
            }
            i++;
        }
        if (i == hexChar.length) {
            return -1;
        }
        return i;
    }
}