import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    public static BufferedImage[] player;
    public static BufferedImage[] ghost;

    public BufferedImage spritesheet;

    public Texture() {
        try {
            spritesheet = ImageIO.read(getClass().getResource("/sprites/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new BufferedImage[4];
        ghost = new BufferedImage[2];

        player[0] = getSprite(0, 0);
        player[1] = getSprite(16, 0);
        player[2] = getSprite(32, 0);
        player[3] = getSprite(48, 0);

        ghost[0] = getSprite(0, 16);
        ghost[1] = getSprite(16, 16);
    }

    public BufferedImage getSprite(int xx, int yy) {
        return spritesheet.getSubimage(xx, yy, 15, 15);
    }
}
