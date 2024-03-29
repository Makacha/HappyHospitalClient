package Main;

import javax.swing.JPanel;

import Graphic.*;
import Socket.Connection;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Button;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    public static final int originalTileSize = 16;
    public static final int scale = 2;

    public static final int tileSize = originalTileSize * scale;
    public static final int gameCol = 52;
    public static final int gameRow = 28;

    public static final int gameWidth = gameCol * tileSize;
    public static final int gameHeight = gameRow * tileSize;

    private int xOffSet = 0;
    private int yOffSet = 0;

    private Thread gameThread;
    public static final int fps = 60;
    private int cntFrame = 0;
    private Connection connection = new Connection(this);
    private KeyboardInput keyboardInput = new KeyboardInput(connection);
    private GameMap gameMap = new GameMap(this);
    Button loadMapButton = new Button("Load map!");
    Button saveMapButton = new Button("Save map!");

    public GamePanel() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.white);
        this.addKeyListener(keyboardInput);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setFont(new Font("Computer Modern", Font.BOLD, 28));
        connection.sendRequest("add player");
        connection.sendRequest("add opponent");

        loadMapButton.addActionListener(new LoadMapEvent(connection));
        loadMapButton.setFont(new Font("Arial", Font.PLAIN, 28));
        loadMapButton.setFocusable(false);

        saveMapButton.addActionListener(new SaveMapEvent(connection));
        saveMapButton.setFont(new Font("Arial", Font.PLAIN, 28));
        saveMapButton.setFocusable(false);

        this.add(loadMapButton);
        this.add(saveMapButton);

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

            if (cntFrame % 60 == 0) {
                connection.sendRequest("add NPC");
            }

            if (cntFrame % 1200 == 0) {
                connection.sendRequest("add opponent");
            }

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, gameWidth, gameHeight);
        gameMap.draw(g2);
        connection.sendRequest("update");
        loadMapButton.setBounds(this.getWidth() - 180, 20, 140, 40);
        saveMapButton.setBounds(this.getWidth() - 180, 75, 140, 40);
        Entity[] entities = connection.needDraw();
        for (Entity e : entities) {
            e.draw(g2);
        }
        g2.dispose();
    }

    public void setOffSet(int x, int y) {
        xOffSet = Math.min(gameWidth - this.getWidth(), Math.max(0, x - this.getWidth() / 2));
        yOffSet = Math.min(gameHeight - this.getHeight(), Math.max(0, y - this.getHeight() / 2));
    }

    public int getYOffSet() {
        return yOffSet;
    }

    public int getXOffSet() {
        return xOffSet;
    }
}
