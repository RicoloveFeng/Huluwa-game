package huluwa;

import huluwa.creatures.*;
import huluwa.lands.*;
import huluwa.utils.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import static huluwa.utils.Utils.Log;
import static huluwa.utils.Utils.range;

public class BattleField {
    public final int width = 18;
    public final int height = 14;
    private Land[][] field;

    private static ArrayList<Thread> creatureThreads;
    public static Creature[] processQueue;
    public static int queueLen;
    public static boolean queueEnable;
    public static boolean running;
    public static Lock lock;
    public static CountDownLatch cdl;

    public Land getLand(int col, int row) {
        if (0 <= col && col < height && 0 <= row && row < width)
            return field[col][row];
        else
            return null;
    }

    public Land getLand(Position pos) {
        return getLand(pos.col(), pos.row());
    }

    BattleField() {
        Random random = new Random();
        field = new Land[height][width];
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                if (5 < j && j < 12) {
                    double roll = random.nextDouble();
                    if (roll < 0.04) {
                        field[i][j] = new Rock();
                    } else if (0.04 <= roll && roll < 0.14) {
                        field[i][j] = new Water();
                    } else if (0.14 <= roll && roll < 0.29) {
                        field[i][j] = new Dirt();
                    } else {
                        field[i][j] = new Plain();
                    }
                } else {
                    field[i][j] = new Plain();
                }
            }
        }
    }
}
