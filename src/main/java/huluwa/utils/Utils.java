package huluwa.utils;

import huluwa.Range;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.IOException;
import java.util.Scanner;

public class Utils {
    public enum MENU_STATE{
        NONE, START, HISTORY
    }

    public enum DIRECTION{
        TO_BAD, TO_HULUWA
    }

    public enum MOVE{
        MOVE, ATTACK, CHEER, DECADE, MISS
    }
    public final static int LARGE_NUM = 1000000;
    public final static int GRID_WIDTH = 18;
    public final static int GRID_HEIGHT = 14;
    public final static int GRID_LEN = 50;
    public final static int SCENE_WIDTH = 900;
    public final static int SCENE_HEIGHT = 700;
    public final static Range STARTBOX_X = new Range(140, 800);
    public final static Range STARTBOX_Y = new Range(400, 480);
    public final static Range LBOX_X = STARTBOX_X;
    public final static Range LBOX_Y = new Range(540, 620);
    /*
    * Print given string or character with short method name.
    * */
    public static void Log(char c){
        System.out.println(c);
    }

    public static void Log(String msg){
        System.out.println(msg);
    }

    public static void Log(String msg, String end){
        System.out.print(msg);
        System.out.print(end);
    }

    public static int[] range(int n) {
        int[] result = new int[n];
        for(int i = 0; i < n; i++)
            result[i] = i;
        return result;
    }
    // Produce a sequence [start..end)
    public static int[] range(int start, int end) {
        int sz = end - start;
        int[] result = new int[sz];
        for(int i = 0; i < sz; i++)
            result[i] = start + i;
        return result;
    }
    // Produce a sequence [start..end) incrementing by step
    public static int[] range(int start, int end, int step) {
        int sz = (end - start)/step;
        int[] result = new int[sz];
        for(int i = 0; i < sz; i++)
            result[i] = start + (i * step);
        return result;
    }
}
