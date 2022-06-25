package Graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Main.GamePanel;

public class Image {
    private BufferedImage images[];
    private int imageCol;
    private int imageRow;

    public static final Image PLAYER = new Image("res/Sprite/player.png");
    public static final Image OPPONENT = new Image("res/Sprite/opponent.png");
    public static final Image NPC = new Image("res/Sprite/NPC.png");

    public Image(int imageCol, int imageRow, String path) {
        this.imageCol = imageCol;
        this.imageRow = imageRow;

        images = new BufferedImage[imageRow * imageCol + 1];
        int tileSize = GamePanel.tileSize;
        try {
            BufferedImage allImage = ImageIO.read(new File(path));
            for (int i = 0; i < imageRow; ++i) {
                for (int j = 0; j < imageCol; ++j) {
                    images[i * imageCol + j + 1] = allImage.getSubimage(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Image(String path) {
        try {
            BufferedImage allImage = ImageIO.read(new File(path));
            int originalTileSize = GamePanel.originalTileSize;
            int col = allImage.getWidth() / originalTileSize;
            int row = allImage.getHeight() / originalTileSize;
            images = new BufferedImage[row * col + 1];

            for (int j = 0; j < row; ++j) {
                for (int i = 0; i < col; ++i) {
                    images[j * col + i + 1] = allImage.getSubimage(i * originalTileSize, j * originalTileSize,
                            originalTileSize, originalTileSize);
                }
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public BufferedImage getImage(int id) {
        return images[id];
    }

    public int getImageCol() {
        return imageCol;
    }

    public int getImageRow() {
        return imageRow;
    }
}
