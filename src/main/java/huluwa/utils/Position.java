package huluwa.utils;

import javafx.geometry.Pos;

public class Position {
    private int i, j;

    public Position(int col, int row) {
        i = col;
        j = row;
    }

    public int col() {
        return i;
    }

    public int row() {
        return j;
    }

    public void setPos(int col, int row) {
        i = col;
        j = row;
    }

    public void offset(int col, int row) {
        i += col;
        j += row;
    }

    public void scale(int scaleCol, int scaleRow) {
        i *= scaleCol;
        j *= scaleRow;
    }

    public boolean equals(Object o) {
        if(o instanceof Position){
            Position b = (Position) o;
            return i == b.i && j == b.j;
        }
        return super.equals(o);
    }

    public static int distance(Position a, Position b) {
        return Math.abs(a.col() - b.col()) + Math.abs(a.row() - b.row());
    }

    public String toString(){
        return "("+i+","+j+")";
    }
}
