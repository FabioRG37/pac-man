import java.awt.*;
import java.io.Serial;

public class Tile extends Rectangle {

    @Serial
    private static final long serialVersionUID = 1L;

    public Tile(int x, int y) {
        setBounds(x, y, 32, 32);
    }
    public void render(Graphics g) {
        g.setColor(new Color(63,72,204));
        g.fillRect(x, y, width, height);
    }
}
