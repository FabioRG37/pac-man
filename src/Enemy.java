import java.awt.*;
import java.io.Serial;

public class Enemy extends Rectangle {

    @Serial
    public static final long serialVersionUID = 1L;

    public Enemy(int x, int y) {
        setBounds(x, y, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 32, 32);
    }
}
