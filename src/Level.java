import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public int width;
    public int height;

    public Tile[][] tiles;

    public List<Apple> apples;
    public List<Enemy> enemies;

    public Level(String path) {
        apples = new ArrayList<>();
        enemies = new ArrayList<>();
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            this.width = map.getWidth();
            this.height = map.getHeight();
            int[] pixels = new int[width * height];
            tiles = new Tile[width][height];
            map.getRGB(0, 0, width, height, pixels, 0, width);

            for (int xx = 0; xx < width; xx++) {
                for (int yy = 0; yy < height; yy++) {
                    int val = pixels[xx + (yy * width)];

                    if (val == 0xFF000000) {
                        //Tile
                        tiles[xx][yy] = new Tile(xx * 32, yy * 32);
                    } else if (val == 0xFF0000FF) {
                        //Player
                        //Game.player.x = xx * 32;
                        //Game.player.y = yy * 32;
                    } else if (val == 0xFFED1C24) {
                        //Enemy
                        //enemies.add(new Enemy(xx * 32, yy * 32));
                    } else if (val == 0xFFFFFFFF) {
                        //FRUIT
                        apples.add(new Apple(xx * 32, yy * 32));
                    }
                }
            }
            Game.player.x = 320;
            Game.player.y = 352;
            for (int e = 0; e < 4; e ++) {
                enemies.add(new Enemy(288, 192));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tick() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).tick();
        }
    }

    public void render(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y] != null) tiles[x][y].render(g);
            }
        }

        for (int i = 0; i < apples.size(); i++) {
            apples.get(i).render(g);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }
    }
}
