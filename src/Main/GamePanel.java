package Main;
import javax.swing.JPanel;

import Graphic.*;
import Socket.Connection;

import java.awt.Dimension;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    public static final int originalTileSize = 16;
    public static final int scale = 2;

    public static final int tileSize = originalTileSize * scale;
    public static final int screenCol = 52;
    public static final int screenRow = 28;

    public static final int screenWidth = screenCol * tileSize;
    public static final int screenHeight = screenRow * tileSize;

    private Thread gameThread;
    private int fps = 60;
    private int cntFrame = 0;
    private Connection connection = new Connection();
    private KeyboardInput keyboardInput = new KeyboardInput(connection);
    private GameMap gameMap = new GameMap(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.addKeyListener(keyboardInput);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        connection.sendRequest("add player");
        connection.sendRequest("add opponent");

    }

    public int getCntFrame() {
        return this.cntFrame;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void resetGameThread() {
        gameThread = new Thread(this);
        gameThread.run();
    }

    @Override
    public void run() {
        double drawTimeGap = 1000000000 / fps;
        double drawTime = System.nanoTime() + drawTimeGap;

        while (gameThread != null) {

            if( cntFrame % 60 == 0){
                connection.sendRequest("add NPC");
            }

            update();

            repaint();

            ++cntFrame;

            try {
                double remainTime = drawTime - System.nanoTime();
                remainTime /= 1000000;

                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);

                drawTime += drawTimeGap;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        connection.disconect();
    }

    
    public void update() {
        connection.sendRequest("update");
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        gameMap.draw(g2);
        Entity[] entities = connection.needDraw();
        for (Entity e : entities) {
            e.draw(g2);
        }
        g2.dispose();
    }
}
