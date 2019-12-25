package huluwa.utils;

import java.io.Serializable;

public class DrawRecord implements Serializable {
    public String src;
    public int x;
    public int y;
    public int order;

    public DrawRecord(String src, int x, int y, int order) {
        this.src = src;
        this.x = x;
        this.y = y;
        this.order = order;
    }
}
