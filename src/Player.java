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
        if (right && canMove(x + speed,y)) x += speed;
        if (left && canMove(x - speed,y)) x -= speed;
        if (up && canMove(x,y - speed)) y -= speed;
        if (down && canMove(x, y + speed)) y += speed;
    }

    private boolean canMove(int nestx, int nexty) {
        Rectangle bounds = new Rectangle(nestx, nexty, width, height);
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
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
}
