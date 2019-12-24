package huluwa;

import huluwa.utils.DrawRecord;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static huluwa.utils.Utils.*;

import java.io.*;

public class GUIMain extends Application {
    private GameManager gm;

    static public void main(String... argv) throws IOException {
        launch(argv);
    }

    MENU_STATE getState(int x, int y) {
        MENU_STATE newState;
        if (STARTBOX_X.inRange(x) && STARTBOX_Y.inRange(y)) {
            newState = MENU_STATE.START;
        } else if (LBOX_X.inRange(x) && LBOX_Y.inRange(y)) {
            newState = MENU_STATE.HISTORY;
        } else {
            newState = MENU_STATE.NONE;
        }
        return newState;
    }

    File getFile(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("选择日志文件...");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("日志文件", "*.log"));
        return chooser.showOpenDialog(stage);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene s = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        //Log(this.getClass().getClassLoader().getResource("Main.png").toString());
        //gc.drawImage(new Image(this.getClass().getResource("Main.png").toString()), 0, 0);
        gc.drawImage(new Image(this.getClass().getClassLoader().getResource("Main.png").toString()),0,0);

        primaryStage.setTitle("正版葫芦娃自走棋");
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResource("Template.png").toString()));
        primaryStage.setScene(s);
        primaryStage.setHeight(SCENE_HEIGHT + 30);
        primaryStage.setWidth(SCENE_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.show();

        s.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (GameManager.waitingKey) {
                    // let player press any key
                    if (GameManager.isGaming) {
                        Log("gm play");
                        gm.play();
                    } else {
                        Log("gm end");
                        gm.end();
                    }
                } else if (!GameManager.isGaming) {
                    String key = event.getCode().getName();
                    if (key.equals("Space")) {
                        Log("gm ready");
                        try {
                            gm = new GameManager(gc, s, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gm.ready();
                    } else if (key.equals("L")) {
                        File logfile = getFile(primaryStage);
                        if (logfile != null) {
                            try {
                                gm = new GameManager(gc, s, true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            gm.replay(logfile);
                        } else {
                            Log("File not exist");
                        }
                    } else if (key.equals("Q")) {
                        System.exit(-1);
                    }
                }
            }
        });


        s.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!GameManager.isGaming && !GameManager.waitingKey) {
                    // draw box line
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    gc.drawImage(new Image(this.getClass().getClassLoader().getResource("Main.png").toString()),0,0);
                    gc.setStroke(Color.YELLOW);
                    MENU_STATE newState = getState(x, y);
                    if (newState == MENU_STATE.START) {
                        gc.strokeLine(STARTBOX_X.min, STARTBOX_Y.max, STARTBOX_X.max, STARTBOX_Y.max);
                    } else if (newState == MENU_STATE.HISTORY) {
                        gc.strokeLine(LBOX_X.min, LBOX_Y.max, LBOX_X.max, LBOX_Y.max);
                    }
                }
            }
        });

        s.setOnMouseClicked(event -> {
            if (!GameManager.isGaming && !GameManager.waitingKey) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                MENU_STATE newState = getState(x, y);
                if (newState == MENU_STATE.START) {
                    try {
                        gm = new GameManager(gc, s, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gm.ready();
                } else if (newState == MENU_STATE.HISTORY) {
                    File logfile = getFile(primaryStage);
                    if (logfile != null) {
                        try {
                            gm = new GameManager(gc, s, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gm.replay(logfile);
                    } else {
                        Log("File not exist");
                    }
                }
            }
        });

        primaryStage.setOnCloseRequest(event -> {
            Log("Windows closed");
            System.exit(0);
        });
    }
}
