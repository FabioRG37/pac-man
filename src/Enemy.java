import java.awt.*;
import java.io.Serial;
import java.util.Random;

public class Enemy extends Rectangle {

    @Serial
    public static final long serialVersionUID = 1L;

    private int random = 0, smart = 1, find_path = 2;
    private int state = smart;
    private int right = 0, left = 1, up = 2, down = 3;
    private int dir = -1;
    public Random randomGen;
    private int time = 0, timeIndex = 0;
    private int targetTime = 60 * 2, targetTimeIndex = 18, imageIndex = 0;
    private int spd = 2;
    private int lastDir = -1, lastSprite = 1;

    public Enemy(int x, int y) {
        randomGen = new Random();
        setBounds(x, y, 32, 32);
        dir = randomGen.nextInt(4);
    }

    public void tick() {
        if (state == random) {

            if (dir == right) {
                if (canMove(x + spd, y)) {
                    if (randomGen.nextInt(100) > 50){
                        lastSprite = 1;
                        x += spd;
                    }
                } else {
                    dir = randomGen.nextInt(4);
                }
            } else if (dir == left) {
                if (canMove(x - spd, y)) {
                    if (randomGen.nextInt(100) > 50) {
                        lastSprite = -1;
                        x -= spd;
                    }
                } else {
                    dir = randomGen.nextInt(4);
                }
            } else if (dir == up) {
                if (canMove(x, y - spd)) {
                    if (randomGen.nextInt(100) > 50) y -= spd;
                } else {
                    dir = randomGen.nextInt(4);
                }
            } else if (dir == down) {
                if (canMove(x, y + spd)) {
                    if (randomGen.nextInt(100) > 50) y += spd;
                } else {
                    dir = randomGen.nextInt(4);
                }

            }
            time++;
            timeIndex++;
            if (time == targetTime) {
                state = smart;
                time = 0;
            }
            if (timeIndex == targetTimeIndex) {
                timeIndex = 0;
                imageIndex++;
            }
        } else if (state == smart) {
            //follow the player
            boolean move = false;

            if (x < Game.player.x) {
                if (canMove(x + spd, y)) {
                    if (randomGen.nextInt(100) > 50){
                        lastSprite = 1;
                        x += spd;
                    }
                    move = true;
                    lastDir = right;
                }
            }

            if (x > Game.player.x) {
                if (canMove(x - spd, y)) {
                    if (randomGen.nextInt(100) > 50) {
                        lastSprite = -1;
                        x -= spd;
                    }
                    move = true;
                    lastDir = left;
                }
            }

            if (y < Game.player.y) {
                if (canMove(x, y + spd)) {
                    if (randomGen.nextInt(100) > 50) y += spd;
                    move = true;
                    lastDir = down;
                }
            }

            if (y > Game.player.y) {
                if (canMove(x, y - spd)) {
                    if (randomGen.nextInt(100) > 50) y -= spd;
                    move = true;
                    lastDir = up;
                }
            }

            if (x == Game.player.x && y == Game.player.y) move = true;

            if (!move) {
                state = find_path;
            }
            time++;
            timeIndex++;
            if (time == targetTime) {
                state = smart;
                time = 0;
            }
            if (timeIndex == targetTimeIndex) {
                timeIndex = 0;
                imageIndex++;
            }
        } else if (state == find_path) {

            if (lastDir == right) {
                if (y < Game.player.y) {
                    if (canMove(x, y + spd)) {
                        if (randomGen.nextInt(100) > 50) y += spd;
                        state = smart;
                    } else {
                        if (canMove(x, y - spd)) {
                            if (randomGen.nextInt(100) > 50) y -= spd;
                            state = smart;
                        }
                    }
                }
                if (canMove(x + spd, y)) {
                    if (randomGen.nextInt(100) > 50) {
                        lastSprite = 1;
                        x += spd;
                    }
                }
            } else if (lastDir == left) {
                if (y < Game.player.y) {
                    if (canMove(x, y + spd)) {
                        if (randomGen.nextInt(100) > 50) y += spd;
                        state = smart;
                    } else {
                        if (canMove(x, y - spd)) {
                            if (randomGen.nextInt(100) > 50) y -= spd;
                            state = smart;
                        }
                    }
                }
                if (canMove(x - spd, y)) {
                    if (randomGen.nextInt(100) > 50) {
                        lastSprite = -1;
                        x -= spd;
                    }
                }
            } else if (lastDir == up) {
                if (x < Game.player.x) {
                    if (canMove(x + spd, y)) {
                        if (randomGen.nextInt(100) > 50) {
                            lastSprite = 1;
                            x += spd;
                        }
                        state = smart;
                    } else {
                        if (canMove(x - spd, y)) {
                            if (randomGen.nextInt(100) > 50) {
                                lastSprite = -1;
                                x -= spd;
                            }
                            state = smart;
                        }
                    }
                }
                if (canMove(x, y - spd)) {
                    if (randomGen.nextInt(100) > 50) y -= spd;
                }
            } else if (lastDir == down) {
                if (x < Game.player.x) {
                    if (canMove(x + spd, y)) {
                        if (randomGen.nextInt(100) > 50) {
                            lastSprite = 1;
                            x += spd;
                        }
                        state = smart;
                    } else {
                        if (canMove(x - spd, y)) {
                            if (randomGen.nextInt(100) > 50) {
                                lastSprite = -1;
                                x -= spd;
                            }
                            state = smart;
                        }
                    }
                }
                if (canMove(x, y + spd)) {
                    if (randomGen.nextInt(100) > 50) y += spd;
                }
            }
            time++;
            timeIndex++;
            if (time == targetTime) {
                state = smart;
                time = 0;
            }
            if (timeIndex == targetTimeIndex) {
                timeIndex = 0;
                imageIndex++;
            }
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
        if (lastSprite == 1) g.drawImage(Texture.ghost[imageIndex % 2], x, y, width, height, null);
        else g.drawImage(Texture.ghost[imageIndex % 2], x + 32, y, -width, height, null);
    }
}
