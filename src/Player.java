import java.io.Serial;
import java.awt.*;

public class Player extends Rectangle {

    @Serial
    private static final long serialVerionUID = 1L;

    public boolean right, left, up, down;
    private int speed = 4;

    public Player(int x, int y) {
        setBounds(x, y, 32, 32);
    }

    public void tick() {
        if (right && canMove(x + speed, y)) x += speed;
        if (left && canMove(x - speed, y)) x -= speed;
        if (up && canMove(x, y - speed)) y -= speed;
        if (down && canMove(x, y + speed)) y += speed;

        Level level = Game.level;
        for (int i = 0; i < level.apples.size(); i++) {
            if (this.intersects(level.apples.get(i))) {
                Game.points++;
                level.apples.remove(i);
                break;
            }
        }

        if (level.apples.isEmpty()) {
            //End game, you won!
//            Game.player = new Player(0,0);
//            Game.level = new Level("/map/map.png");
            Game.STATE = Game.PAUSE_SCREEN;
            return;
        }
        for (int i = 0; i < Game.level.enemies.size(); i++) {
            Enemy en = Game.level.enemies.get(i);
            if (en.intersects(this)) {
                // System.exit(1);
                //Menu System
                Game.points = 0;
                Game.STATE = Game.PAUSE_SCREEN;
            }
        }
    }

    private boolean canMove(int nextx, int nexty) {
        Rectangle bounds = new Rectangle(nextx, nexty, width, height);
        Level level = Game.level;

        for (int xx = 0; xx < level.tiles.length; xx++) {
            for (int yy = 0; yy < level.tiles[0].length; yy++) {
                if (level.tiles[xx][yy] != null) {
                    if (bounds.intersects(level.tiles[xx][yy])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void render(Graphics g) {
        g.drawImage(Texture.player, x, y, width, height, null);
    }
}
