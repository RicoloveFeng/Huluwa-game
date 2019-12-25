package huluwa.utils;

import javafx.geometry.Pos;

public class Position {
    private Pair<Integer, Integer> pos;

    public Position(int col, int row) {
        pos = new Pair<>(col, row);
    }

    public int col() {
        return pos.getK();
    }

    public int row() {
        return pos.getV();
    }

    public void setPos(int col, int row) {
        pos.setK(col);
        pos.setV(row);
    }

    public void offset(int col, int row) {
        pos.setK(pos.getK()+col);
        pos.setV(pos.getV()+row);
    }

    public void scale(int scaleCol, int scaleRow) {
        pos.setK(pos.getK()*scaleCol);
        pos.setV(pos.getV()*scaleRow);
    }

    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position b = (Position) o;
            return pos.getK().equals(b.pos.getK()) && pos.getV().equals(b.pos.getV());
        }
        return super.equals(o);
    }

    public static int distance(Position a, Position b) {
        return Math.abs(a.col() - b.col()) + Math.abs(a.row() - b.row());
    }

    public String toString() {
        return "(" + pos.getK() + "," + pos.getV() + ")";
    }
}
