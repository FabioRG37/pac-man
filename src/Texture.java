import java.awt.image.BufferedImage;

public class Texture {
    public static BufferedImage player;
    public static BufferedImage ghost;

    public Texture(){
        player = Game.spriteSheet.getSprite(0, 0);
        ghost = Game.spriteSheet.getSprite(0, 16);
    }
}
