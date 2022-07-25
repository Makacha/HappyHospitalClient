package Graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public class Entity {
    private String name;
    private int state;
    private int x;
    private int y;
    private int size;
    private BufferedImage image;
    private GamePanel gamePanel;

    public Entity(String imageInfo, GamePanel gamePanel) {
        String[] tmp = imageInfo.split(" ");
        name = tmp[0];
        state = Integer.parseInt(tmp[1]);
        if (name.equals("slower"))
            return;
        x = Integer.parseInt(tmp[2]);
        y = Integer.parseInt(tmp[3]);
        size = Integer.parseInt(tmp[4]);
        this.gamePanel = gamePanel;
        if (name.equals("player")) {
            image = Image.PLAYER.getImage(state);
            gamePanel.setOffSet(x, y);
            size *= 2;
        } else if (name.equals("opponent")) {
            image = Image.OPPONENT.getImage(state);
            size *= 2;
        } else if (name.equals("NPC")) {
            image = Image.NPC.getImage(state);
        }
    }

    public void draw(Graphics2D g2) {
        if (name.equals("slower")) {
            g2.setColor(Color.RED);
            g2.drawString("You are slower than " + state + " enemies!", 400, 30);
        } else {
            g2.drawImage(image, x - gamePanel.getXOffSet() - (size - GamePanel.originalTileSize) / 2,
                    y - gamePanel.getYOffSet() - (size - GamePanel.originalTileSize) / 2, size, size, null);
        }
    }
}
