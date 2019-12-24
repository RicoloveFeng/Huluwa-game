package huluwa;

import huluwa.creatures.*;
import huluwa.exceptions.MoveToOutsideOfField;
import huluwa.exceptions.StrategyOutOfPosition;
import huluwa.lands.Land;
import huluwa.strategy.*;
import huluwa.ui.drawEngine;
import huluwa.utils.DrawRecord;
import huluwa.utils.MoveRecord;
import huluwa.utils.Position;
import huluwa.utils.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeSupport;
import java.io.*;
import java.io.IOException;
import java.util.*;


import static huluwa.utils.Utils.*;
import static java.lang.Integer.min;

public class GameManager {
    private final Date timestamp;
    private ArrayList<IStrategy> strategies;
    private int creatureCount;
    private int round;
    private static Team huluwa;
    private static Team badguy;
    private static BattleField bf;
    private static GraphicsContext gc;
    private static Scene s;
    private ProcessQueue pq;
    private drawEngine de;
    private Random random;

    private int orderCount;
    private int index;
    private ArrayList<DrawRecord> drs;
    private Timeline replayTimeline;

    public static boolean isGaming;
    public static boolean waitingKey;

    static {
        isGaming = false;
        waitingKey = false;
    }

    public class ProcessQueue {
        public ICreature[] queue;
        public int volume;
        public int queueLen;
        public boolean isRunning;

        public PropertyChangeSupport lenPCS;

        public void initQueue(int len) {
            queue = new ICreature[len];
            volume = len;
            queueLen = 0;
            lenPCS = new PropertyChangeSupport(this);
        }

        public void joinQueue(ICreature creature){
            queue[queueLen++] = creature;
            lenPCS.firePropertyChange("queueFull",queueLen - 1,queueLen);
        }

        public void cleanQueue(){
            Arrays.fill(queue, null);
            queueLen = 0;
        }

        public boolean isNull(){
            return queueLen == 0;
        }
    }

    private void initStrategies() {
        strategies = new ArrayList<>();
        strategies.add(new Arrow());
        strategies.add(new Birdfly());
        strategies.add(new Birdwing());
        strategies.add(new Defend());
        strategies.add(new Fish());
        strategies.add(new Moon());
        strategies.add(new Snake());
        strategies.add(new Square());
    }

    private IStrategy getOneStrategy() {
        Random random = new Random();
        IStrategy ret = null;
        try {
            ret = strategies.get(random.nextInt(strategies.size())).getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return ret;
    }

    private void makeHuluwa(@NotNull IStrategy s) {
        Position base = new Position(7, 0);
        huluwa = new Team();
        try {
            //大娃攻击力比较高，行动缓慢
            huluwa.add(new Huluwa("Da.png", 50, 15, 0.08, new Range(1, 1), new Range(0, 1), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            //二娃敏捷高，容易抵挡攻击，但是攻击力相对弱一些
            huluwa.add(new Huluwa("Er.png", 40, 5, 0.5, new Range(1, 1), new Range(0, 5), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            //三娃比较肉
            huluwa.add(new Huluwa("San.png", 70, 8, new Range(1, 1), new Range(0, 2), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            //四娃和五娃可以远程攻击
            huluwa.add(new Huluwa("Si.png", 45, 8, new Range(1, 2), new Range(0, 2), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            huluwa.add(new Huluwa("Wu.png", 45, 6, new Range(1, 3), new Range(0, 2), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            //六娃可以隐身，所以来无影去无踪
            huluwa.add(new Huluwa("Liu.png", 45, 8, 0.12, new Range(1, 1), new Range(0, 3), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            //七娃纯粹就是来丢人的
            huluwa.add(new Huluwa("Qi.png", 45, 8, new Range(1, 1), new Range(0, 2), s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
            huluwa.setCheerleader(new Grandpa(s.nextPosition(Utils.DIRECTION.TO_BAD, base)));
        } catch (StrategyOutOfPosition e) {
            e.printStackTrace();
        }
    }

    private void makeBadguy(@NotNull IStrategy s) {
        Position base = new Position(7, 17);
        badguy = new Team();
        try {
            int memberCount = 2;
            badguy.add(new Tornedron(s.nextPosition(Utils.DIRECTION.TO_HULUWA, base)));
            badguy.setCheerleader(new Basilisk(s.nextPosition(Utils.DIRECTION.TO_HULUWA, base)));
            while (memberCount < s.getLimit()) {
                badguy.add(new Minion(s.nextPosition(Utils.DIRECTION.TO_HULUWA, base), memberCount - 2));
                memberCount += 1;
            }
        } catch (StrategyOutOfPosition e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Position> calculatePath(Position begin, Position end) {
        ArrayList<Position> openSet = new ArrayList<>();
        ArrayList<Position> closeSet = new ArrayList<>();
        ArrayList<Position> path = new ArrayList<>();
        int[][] cost = new int[bf.height][bf.width];
        Position[][] parent = new Position[bf.height][bf.width];
        for (int i : range(bf.height)) {
            for (int j : range(bf.width)) {
                cost[i][j] = LARGE_NUM;
                parent[i][j] = null;
            }
        }
        openSet.add(begin);
        cost[begin.col()][begin.row()] = 0;
        parent[begin.col()][begin.row()] = begin;
        Position now_pos = begin;
        while (!openSet.isEmpty()) {
            int now_cost = LARGE_NUM;
            now_pos = null;
            // get min cost point
            for (Position p : openSet) {
                if (cost[p.col()][p.row()] < now_cost) {
                    now_cost = cost[p.col()][p.row()];
                    now_pos = p;
                }
            }
            //Log("[calPath] processing "+now_pos.col()+" "+now_pos.row());
            // if find target, ready to return
            assert now_pos != null;
            if (now_pos.equals(end)) {
                // get path
                break;
            }
            // if not find target
            else {
                openSet.remove(now_pos);
                closeSet.add(now_pos);
                int[][] direction = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
                // for all adjacent land
                for (int[] d : direction) {
                    Position m = new Position(now_pos.col() + d[0], now_pos.row() + d[1]);
                    Land targetLand = bf.getLand(m.col(), m.row());
                    if (targetLand != null && !targetLand.isBlocking()) {
                        // in close set?
                        if (closeSet.contains(m)) {
                            //do nothing
                        } else if (openSet.contains(m)) {
                            if (cost[m.col()][m.row()] + 1 < cost[now_pos.col()][now_pos.row()]) {
                                cost[now_pos.col()][now_pos.row()] = cost[m.col()][m.row()] + 1;
                                parent[now_pos.col()][now_pos.row()] = m;
                            }
                        } else {
                            if (m.equals(end) || (!huluwa.isOccupying(m) && !badguy.isOccupying(m))) {
                                parent[m.col()][m.row()] = now_pos;
                                cost[m.col()][m.row()] = cost[now_pos.col()][now_pos.row()] + 1;
                                openSet.add(m);
                            }
                        }
                    }
                }
            }
        }
        if (now_pos.equals(end)) {
            do {
                path.add(0, parent[now_pos.col()][now_pos.row()]);
                now_pos = parent[now_pos.col()][now_pos.row()];
            } while (now_pos != begin);
        }
        return path;
    }

    private MoveRecord moveCreature(ICreature mover, ArrayList<Position> path) {
        Range mr = mover.getMoveRange();
        assert mr.min == 0;
        Position dest;
        if (path.size() == 0) { // no path available
            dest = mover.getPosition();
        } else {
            dest = path.get(min(path.size() - 1, mr.max));
        }
        Position ori = new Position(mover.getPosition().col(),mover.getPosition().row());
        try {
            mover.moveTo(dest.col(), dest.row());
        } catch (MoveToOutsideOfField moveToOutsideOfField) {
            moveToOutsideOfField.printStackTrace();
        }
        return new MoveRecord(mover, mover, ori, dest, MOVE.MOVE);
    }

    private ArrayList<MoveRecord> generateProcess(){
        ArrayList<MoveRecord> ret = new ArrayList<>();
        for (ICreature creature : pq.queue) {
            // see whether you are fighter or cheerleader
            if (creature.isAlive()) {
                // first move the character
                Position targetPosition;
                if (creature instanceof Cheerleader) {
                    if (creature instanceof Grandpa) {
                        targetPosition = huluwa.bestCheerPosition(bf);
                    } else { // if Basilisk
                        targetPosition = badguy.bestCheerPosition(bf);
                    }
                }
                else { // if instance of Fighter
                    // if no one to fight, game ends.
                    if (badguy.getAliveMembers().size() == 0 || huluwa.getAliveMembers().size() == 0) break;

                    // pick nearest enemy
                    Fighter fighter = (Fighter) creature;
                    if (creature instanceof Huluwa) {
                        fighter.setTarget(badguy.getNearsetMember(creature.getPosition()));
                    } else {
                        fighter.setTarget(huluwa.getNearsetMember(creature.getPosition()));
                    }

                    // calculate path to target
                    targetPosition = fighter.getTarget().getPosition();
                }
                //get target pos, try to move
                ArrayList<Position> path = calculatePath(creature.getPosition(), targetPosition);

                // move according to path
                ret.add(moveCreature(creature, path));

                //try to perform operation
                if(creature instanceof Fighter){
                    Fighter fighter = (Fighter) creature;
                    // see if target in attack range
                    if (fighter.getAttackRange().inRange(Position.distance(creature.getPosition(), targetPosition))) {
                        // get personal ad and land buff
                        int damage = fighter.getAttackDamage() + bf.getLand(creature.getPosition()).attackBuff();
                        //try get cheer buff
                        Cheerleader cheerleader = null;
                        if(huluwa.contains(creature) &&
                        huluwa.getCheerleader().isAlive()){
                            cheerleader = huluwa.getCheerleader();

                        }
                        else if(badguy.contains(creature) && badguy.getCheerleader().isAlive()){
                            cheerleader = badguy.getCheerleader();

                        }
                        if(cheerleader != null && cheerleader.getCheerRange().inRange(Position.distance(cheerleader.getPosition(), creature.getPosition()))){
                            damage += cheerleader.attackBuff();
                        }
                        // roll for miss
                        if(random.nextDouble() <= fighter.getTarget().getMissRate()){
                            ret.add(new MoveRecord(creature, fighter.getTarget(), creature.getPosition(), fighter.getTarget().getPosition(), MOVE.MISS));
                        }else{
                            fighter.getTarget().takeDamage(damage);
                            ret.add(new MoveRecord(creature, fighter.getTarget(), creature.getPosition(), fighter.getTarget().getPosition(), MOVE.ATTACK));
                        }
                    }

                    if(!fighter.getTarget().isAlive()){
                        Position tpos = fighter.getTarget().getPosition();
                        ret.add(new MoveRecord(fighter.getTarget(),fighter.getTarget(),tpos,tpos,MOVE.DECADE));
                    }
                }
            }
        }
        return ret;
    }

    GameManager(GraphicsContext graphicsContext, Scene scene, boolean isReplay) throws IOException {
        timestamp = new Date();
        gc = graphicsContext;
        s = scene;
        de = new drawEngine(gc, s, timestamp, isReplay);
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,SCENE_WIDTH,SCENE_HEIGHT);
    }

    void ready() {
        // some init works
        bf = new BattleField();
        pq = new ProcessQueue();
        isGaming = true;
        waitingKey = true;
        round = 0;
        random = new Random();

        initStrategies();
        IStrategy sHuluwa, sBadguy;
        sHuluwa = getOneStrategy();
        sBadguy = getOneStrategy();

        makeHuluwa(sHuluwa);
        makeBadguy(sBadguy);
        creatureCount = huluwa.getMemberSize() + badguy.getMemberSize();

        pq.initQueue(creatureCount);
        for(ICreature guy: huluwa){
            guy.setProcessQueue(pq);
        }
        for(ICreature guy: badguy){
            guy.setProcessQueue(pq);
        }

        de.initScene(bf, huluwa, badguy);
    }
    void play(){
        pq.isRunning = true;
        waitingKey = false;

        for (ICreature guy : huluwa) {
            new Thread(guy).start();
        }
        for (ICreature guy : badguy) {
            new Thread(guy).start();
        }

        Log("Thread start all");

        pq.lenPCS.addPropertyChangeListener(evt -> {
            if((int)evt.getNewValue() == creatureCount){
                Log("processing");

                // generate all moves
                ArrayList<MoveRecord> records = generateProcess();

                // pass moves to ui module
                for(MoveRecord rec:records){
                    de.drawPCS.firePropertyChange("rec", null, rec);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // round ends
                pq.cleanQueue();
                round++;
                Log("-----"+round+"-----");
                // if limit reached or all team members die
                if(round == 50 || huluwa.getAliveMembers().size() == 0 || badguy.getAliveMembers().size() == 0){
                    // shutdown game
                    pq.isRunning = false;
                    waitingKey = true;
                    isGaming = false;

                    // show winner
                    de.drawPCS.firePropertyChange("win", null, badguy.getAliveMembers().size() == 0);
                }

                // rerun all threads
                pq.notifyAll();
            }
        });
    }

    private ArrayList<DrawRecord> getReplayBatch(){
        ArrayList<DrawRecord> ret = new ArrayList<>();
        if(index == drs.size()) {
            Log("trying stop");
            replayTimeline.stop();
            isGaming = false;
            waitingKey = true;
            return null;
        }
        while(index < drs.size() && drs.get(index).order == orderCount){
            ret.add(drs.get(index));
            index++;
        }
        orderCount++;
        return ret;
    }

    void replay(File file) {
        isGaming = true;
        ObjectInputStream reader = null;
        drs = new ArrayList<>();
        index = 0;
        orderCount = 0;
        try {
            reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            while(true){
                drs.add((DrawRecord)reader.readObject());
            }
        }
        catch (EOFException e){
            Log("read done");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        orderCount = 0;
        index = 0;
        replayTimeline = new Timeline(
                new KeyFrame(Duration.millis(0),
                        event -> {
                            ArrayList<DrawRecord> batch = getReplayBatch();
                            if(batch != null){
                                for(DrawRecord rec: batch){
                                    try {
                                        de.drawPic(rec.src,rec.x,rec.y,false);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }),
                new KeyFrame(Duration.millis(50))
        );
        replayTimeline.setCycleCount(Timeline.INDEFINITE);
        replayTimeline.play();
    }

    void end(){
        waitingKey = false;
        gc.drawImage(new Image(this.getClass().getClassLoader().getResource("Main.png").toString()),0,0);
    }
}
