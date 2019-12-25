package huluwa.creatures;

import huluwa.GameManager.ProcessQueue;
import huluwa.utils.Range;
import huluwa.exceptions.MoveToOutsideOfField;
import huluwa.utils.Position;

import static huluwa.utils.Utils.*;

public abstract class Creature implements ICreature {
    String id;
    int hp;
    Range moveRange;
    Position position;
    double miss;
    ProcessQueue pq;

    public String getIdentifier() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int d) {
        hp -= d;
        if (hp < 0) hp = 0;
    }

    public Range getMoveRange() {
        return moveRange;
    }

    public Position getPosition() {
        return position;
    }

    public double getMissRate() {
        return miss;
    }

    public void moveTo(int col, int row) throws MoveToOutsideOfField {
        if (0 <= col && col < GRID_HEIGHT && 0 <= row && row < GRID_WIDTH)
            position.setPos(col, row);
        else throw new MoveToOutsideOfField("->" + col + " " + row);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void setProcessQueue(ProcessQueue processQueue) {
        pq = processQueue;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        Log(id + " is running");
        while (true) {
            synchronized (pq) {
                if (!pq.isRunning) {
                    //Log(id+" is stopping");
                    break;
                }
                try {
                    //Log(id+" is joining");
                    if (pq.isNull()) pq.notify();
                    pq.joinQueue(this);
                    pq.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log(id +" is closing");
    }
}
