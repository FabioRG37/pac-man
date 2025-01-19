import java.awt.*;
import java.io.Serial;

public class Apple extends Rectangle {

    @Serial
    private static final long serialVersionUID = 1L;

    public Apple(int x, int y) {
        setBounds(x + 10, y + 8, 8, 8);
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}
