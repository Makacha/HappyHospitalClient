package Graphic;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Main.GamePanel;

public class GameMap {
    private GamePanel gamePanel;
    private Tile path[];
    private Tile building[];
    private Image pathImage, buildingImage;
    private int row, col;

    public GameMap(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        pathImage = new Image(5, 5, "res/Map/path.png");
        buildingImage = new Image(4, 4, "res/Map/building.png");
        row = GamePanel.gameRow;
        col = GamePanel.gameCol;
        path = new Tile[col * row];
        building = new Tile[col * row];
        setPath();
        setBuilding();
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setBuilding() {
        try {
            InputStream is = new FileInputStream("res/Map/building.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int j = 0; j < row; ++j) {
                String line = br.readLine();
                String num[] = line.split(" ");
                for (int i = 0; i < col; ++i) {
                    building[j * col + i] = new Tile();
                    building[j * col + i].setValue(Integer.parseInt(num[i]));
                    if (building[j * col + i].getValue() != 0) {
                        building[j * col + i].setBlock(true);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Tile getBuilding(int id) {
        return building[id];
    }

    public void drawBuilding(Graphics2D g2) {
        int tileSize = GamePanel.tileSize;

        for (int j = 0; j < row; ++j) {
            for (int i = 0; i < col; ++i) {
                if (building[j * col + i].getValue() != 0) {
                    BufferedImage image = buildingImage.getImage(building[j * col + i].getValue());
                    g2.drawImage(image, i * tileSize - gamePanel.getXOffSet(),
                            j * tileSize - gamePanel.getYOffSet(), tileSize, tileSize, null);
                }
            }
        }
    }

    public void setPath() {
        try {
            InputStream is = new FileInputStream("res/Map/path.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int j = 0; j < row; ++j) {
                String line = br.readLine();
                String num[] = line.split(" ");
                for (int i = 0; i < col; ++i) {
                    path[j * col + i] = new Tile();
                    path[j * col + i].setValue(Integer.parseInt(num[i]));
                }
            }
            br.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Tile getPath(int id) {
        return path[id];
    }

    public void drawPath(Graphics2D g2) {
        int tileSize = GamePanel.tileSize;
        int cntframe = this.gamePanel.getCntFrame();
        cntframe %= 32;

        for (int j = 0; j < row; ++j) {
            for (int i = 0; i < col; ++i) {
                if (path[j * col + i].getValue() != 0) {
                    BufferedImage image = null;
                    switch (path[j * col + i].getValue()) {
                        case 1:
                            image = pathImage.getImage(1 + cntframe / 8);
                            break;
                        case 2:
                            image = pathImage.getImage(5 + cntframe / 8);
                            break;
                        case 3:
                            image = pathImage.getImage(9 + cntframe / 8);
                            break;
                        case 4:
                            image = pathImage.getImage(13 + cntframe / 8);
                            break;
                        case 5:
                            image = pathImage.getImage(17);
                            break;
                        case 6:
                            image = pathImage.getImage(18);
                            break;
                        case 7:
                            image = pathImage.getImage(19);
                            break;
                        case 8:
                            image = pathImage.getImage(20);
                            break;
                        case 9:
                            image = pathImage.getImage(21);
                            break;
                        case 10:
                            image = pathImage.getImage(22);
                            break;
                        case 11:
                            image = pathImage.getImage(23);
                            break;
                        case 12:
                            image = pathImage.getImage(24);
                            break;
                        case 13:
                            image = pathImage.getImage(25);
                            break;
                    }
                    g2.drawImage(image, i * tileSize - gamePanel.getXOffSet(),
                            j * tileSize - gamePanel.getYOffSet(), tileSize, tileSize, null);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        drawPath(g2);
        drawBuilding(g2);
    }
}