import java.io.Serial;
import java.awt.*;

public class Player extends Rectangle {

    @Serial
    private static final long serialVerionUID = 1L;

    public boolean right, left, up, down;
    private int speed = 4;
    private int time = 0, targetTime = 18;
    public int imageIndex = 0;
    private int lastDir = 1;
    private Sound sounds = new Sound();

    public Player(int x, int y) {
        setBounds(x, y, 32, 32);
    }

    public void tick() {
        if (right && canMove(x + speed, y)) {
            x += speed;
            lastDir = 1;
        }
        if (left && canMove(x - speed, y)) {
            x -= speed;
            lastDir = -1;
        }
        if (up && canMove(x, y - speed)) y -= speed;
        if (down && canMove(x, y + speed)) y += speed;

        Level level = Game.level;
        for (int i = 0; i < level.apples.size(); i++) {
            if (this.intersects(level.apples.get(i))) {
                sounds.ponto();
                Game.points++;
                level.apples.remove(i);
                break;
            }
        }

        if (level.apples.isEmpty()) {
            //End game, you won!
            Game.STATE = Game.PAUSE_SCREEN;
            return;
        }
        for (int i = 0; i < Game.level.enemies.size(); i++) {
            Enemy en = Game.level.enemies.get(i);
            if (en.intersects(this)) {
                sounds.hit();
                Game.points = 0;
                Game.STATE = Game.PAUSE_SCREEN;
                break;
            }
        }
        time++;
        if (time == targetTime) {
            time = 0;
            imageIndex++;
        }
    }

    private boolean canMove(int nextx, int nexty) {
        Rectangle bounds = new Rectangle(nextx, nexty, width, height);
        Level level = Game.level;
        boolean answer = true;

        for (int xx = 0; xx < level.tiles.length; xx++) {
            for (int yy = 0; yy < level.tiles[0].length; yy++) {
                if (level.tiles[xx][yy] != null) {
                    if (bounds.intersects(level.tiles[xx][yy])) {
                        answer = false;
                    }
                }
            }
        }
        return answer;
    }

    public void render(Graphics g) {
        if (lastDir == 1) g.drawImage(Texture.player[imageIndex % 2], x, y, width, height, null);
        else g.drawImage(Texture.player[imageIndex % 2], x + 32, y, -width, height, null);
    }
}
