import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    private boolean isRunning = false;

    public static final int WIDTH = 640, HEIGHT = 480;
    public static final String TITLE = "Pac-Man";

    private Thread thread;

    public static Player player;
    public static Level level;
    public static SpriteSheet spriteSheet;

    public static final int PAUSE_SCREEN = 0, GAME = 1;
    public static int STATE = -1;
    public boolean isEnter = false;

    private int time = 0;
    private int targetFrames = 35;
    private boolean showText = true;

    public static int points;

    public Game() {
        Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        addKeyListener(this);

        STATE = PAUSE_SCREEN;

        player = new Player(Game.HEIGHT / 2, Game.HEIGHT / 2);
        level = new Level("/map/map.png");
        spriteSheet = new SpriteSheet("/sprites/spritesheet.png");

        points = 0;

        new Texture();
    }

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void tick() {
        if (STATE == GAME) {
            player.tick();
            level.tick();
        } else if (STATE == PAUSE_SCREEN) {
            time++;
            if (time == targetFrames) {
                time = 0;
                if (showText) {
                    showText = false;
                } else {
                    showText = true;
                }
            }
            if (isEnter) {
                isEnter = false;
                player = new Player(Game.HEIGHT / 2, Game.HEIGHT / 2);
                level = new Level("/map/map.png");
                spriteSheet = new SpriteSheet("/sprites/spritesheet.png");

                STATE = GAME;
            }
        }
    }

    private void render() {
        requestFocus();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        if (STATE == GAME) {
            player.render(g);
            level.render(g);
            g.setColor(Color.YELLOW);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("Points: " + points, 278, 471);
        } else if (STATE == PAUSE_SCREEN) {
            int boxWidht = 500;
            int boxHeight = 200;
            int xx = Game.WIDTH / 2 - boxWidht / 2;
            int yy = Game.HEIGHT / 2 - boxHeight / 2;
            g.setColor(new Color(0, 0, 150));
            g.fillRect(xx, yy, boxWidht, boxHeight);

            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 26));
            if (showText) {
                g.drawString("Press ENTER to start the game", xx + 60, yy + 96);
                g.drawString("Press ESC to close the game", xx + 60, yy + 120);
            }
        }
        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        int fps = 0;
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double targetTick = 60.0;
        double delta = 0;
        double ns = (1000000000 / targetTick);

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println(fps);
                fps = 0;
                timer += 1000;
            }
        }

        stop();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle(Game.TITLE);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        game.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (STATE == GAME) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
            if (e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
            if (e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
            if (e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
        } else if (STATE == PAUSE_SCREEN) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                STATE = GAME;
                isEnter = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;

    }
}
