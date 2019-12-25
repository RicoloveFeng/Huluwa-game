package huluwa.ui;

import huluwa.BattleField;
import huluwa.Team;
import huluwa.creatures.ICreature;
import huluwa.lands.Land;
import huluwa.utils.DrawRecord;
import huluwa.utils.MoveRecord;
import huluwa.utils.Position;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Date;

import static huluwa.utils.Utils.*;

public class drawEngine {
    public PropertyChangeSupport drawPCS;
    private GraphicsContext gc;
    private Scene s;
    private BattleField bf;
    private Date ts;
    private ObjectOutputStream writer;
    private int orderCounter;

    public void drawPic(String src, int col, int row, boolean writeFile) throws IOException {
        try {
            gc.drawImage(new Image(this.getClass().getClassLoader().getResource(src).toString()), row * GRID_LEN, col * GRID_LEN);
        } catch (NullPointerException e) {
            Log(src);
            e.printStackTrace();
        }
        if (writeFile) writer.writeObject(new DrawRecord(src, col, row, orderCounter));
    }

    private void drawPic(String src, int col, int row) throws IOException {
        drawPic(src, col, row, true);
    }

    private void drawLand(int col, int row, String landname) throws IOException {
        drawPic(landname, col, row);
    }

    private void drawLand(Position pos, String landname) throws IOException {
        drawLand(pos.col(), pos.row(), landname);
    }

    private void drawCreature(ICreature creature, int col, int row) throws IOException {
        drawPic(creature.getIdentifier(), col, row);
    }

    private void drawCreature(ICreature creature, Position pos) throws IOException {
        drawCreature(creature, pos.col(), pos.row());
    }

    private void drawDead(int col, int row) throws IOException {
        drawPic("Dead.png", col, row);
    }

    private void drawDead(Position pos) throws IOException {
        drawDead(pos.col(), pos.row());
    }

    private void drawHitted(int col, int row) throws IOException {
        drawPic("Attack.png", col, row);
    }

    private void drawHitted(Position pos) throws IOException {
        drawHitted(pos.col(), pos.row());
    }

    private void drawMissed(int col, int row) throws IOException {
        drawPic("Miss.png", col, row);
    }

    private void drawMissed(Position pos) throws IOException {
        drawMissed(pos.col(), pos.row());
    }

    private void drawWinner(boolean win) throws IOException {
        if (!win) {
            drawPic("defeat.png", 0, 0);
        } else {
            drawPic("win.png", 0, 0);
        }
        writer.close();
    }

    public drawEngine(GraphicsContext gc, Scene scene, Date timestamp, boolean isReplay) throws IOException {
        drawPCS = new PropertyChangeSupport(this);
        this.gc = gc;
        s = scene;
        ts = timestamp;
        writer = isReplay ? null : new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("" + timestamp.getTime() + ".log"))));
        orderCounter = 0;

        drawPCS.addPropertyChangeListener(evt -> {
            String eventName = evt.getPropertyName();
            try {
                if (eventName.equals("rec")) {
                    MoveRecord mr = (MoveRecord) evt.getNewValue();
                    if (mr.move == MOVE.MOVE) {
                        drawLand(mr.src, bf.getLand(mr.src).landName());
                        drawCreature(mr.from, mr.dest);
                    } else if (mr.move == MOVE.ATTACK) {
                        drawLand(mr.dest, bf.getLand(mr.dest).landName());
                        drawCreature(mr.to, mr.dest);
                        drawHitted(mr.dest);
                    } else if (mr.move == MOVE.DECADE) {
                        drawLand(mr.src, bf.getLand(mr.src).landName());
                        drawDead(mr.src);
                    } else if (mr.move == MOVE.MISS) {
                        drawLand(mr.dest, bf.getLand(mr.dest).landName());
                        drawCreature(mr.to, mr.dest);
                        drawMissed(mr.dest);
                    }
                    orderCounter++;
                } else if (eventName.equals("win")) {
                    drawWinner((boolean) evt.getNewValue());
                } else if (eventName.equals("replay")) {
                    DrawRecord dr = (DrawRecord) evt.getNewValue();
                    drawPic(dr.src, dr.x, dr.y, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initScene(BattleField battleField, Team team1, Team team2) {
        bf = battleField;
        try {
            for (int i : range(battleField.height)) {
                for (int j : range(battleField.width)) {
                    Land thisLand = battleField.getLand(i, j);
                    drawLand(i, j, thisLand.landName());
                }
            }
            for (ICreature guy : team1) {
                drawCreature(guy, guy.getPosition());
            }
            for (ICreature guy : team2) {
                drawCreature(guy, guy.getPosition());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderCounter++;
    }


}
