package tankrotationexample.game.movable;



import tankrotationexample.GameConstants;
import tankrotationexample.game.*;
import tankrotationexample.game.controllers.Resource;
import tankrotationexample.game.stationary.powerup.HealthPowerUp;
import tankrotationexample.game.stationary.powerup.LivesPowerUp;
import tankrotationexample.game.stationary.powerup.SpeedPowerUp;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank  extends Moveable {




    String id = "tank";
    private int startX;
    private int startY;

    private int playerNum;
    private int speedPowerUpTimer;
    private int prevX;
    private int prevY;
    private TRE tankGame;

    private int x;
    private int y;
    private int vx;
    private int vy;



    private int health;
    private int reloadTimer;
    public int getLives() {
        return lives;
    }


    private int lives;
    private int angle;
    private int camX;
    private int camY;
    private int timeSinceLastRam;

    public void setR(float r) {
        R = r;
    }

    private float R = 3;
    private final float ROTATIONSPEED = 3.0f;

    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean UpPressed;
    private boolean speedBoostActive;



    private ArrayList<Bullet> ammo;

    @Override
    public String getId() {
        return id;
    }


    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }


    public void setLives(int lives) {
        this.lives = lives;
    }


    public int getHealth() {
        return health;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    public boolean isUpPressed() {
        return UpPressed;
    }

    public void setUpPressed(boolean upPressed) {
        UpPressed = upPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public void setDownPressed(boolean downPressed) {
        DownPressed = downPressed;
    }



    public Rectangle getBounds() {
        return this.hitBox.getBounds();
    }


    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, TRE tankGame, int playerNum) {
        super(x, y, angle);
        this.vx = vx;
        this.vy = vy;
        this.prevX = x;
        this.prevY = y;
        this.startX = this.x;
        this.startY = this.y;
        this.img = img;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.ammo = new ArrayList<>();
        this.health = 100;
        this.lives = 4;
        this.active = true;
        this.tankGame = tankGame;
        this.reloadTimer = 100;
        this.playerNum = playerNum;
        this.timeSinceLastRam = 0;
        this.speedPowerUpTimer = 0;
        this.speedBoostActive = false;
    }






    int getX() { return x; }
    int getY() {return y; }
    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }


    public void resetBounds() {this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());}

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed() { this.ShootPressed = true; }

    public void untoggleShootPressed() { this.ShootPressed = false; }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }



    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        prevX = x;
        prevY = y;
        x -= vx;
        y -= vy;
        checkBorder();
        checkCameraBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        prevX = x;
        prevY = y;
        x += vx;
        y += vy;
        checkBorder();
        checkCameraBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveBound() {
        this.hitBox.setLocation(x,y);
    }




    private void checkCameraBorder() {
        camX = x - (GameConstants.GAME_SCREEN_WIDTH / 4); // x = 1752
        camY = y - (GameConstants.GAME_SCREEN_HEIGHT / 2);
        int offsettMaxX = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH + 500;
        int offsettMaxY = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        int offsettMinX = 0;
        int offsettMinY = 0;

        if (camX > offsettMaxX) {
            camX = offsettMaxX;
        }
        else if (camX < offsettMinX) {
            camX = offsettMinX;
        }
        if (camY > offsettMaxY) {
            camY = offsettMaxY;
        }
        else if (camY < offsettMinY) {
            camY = offsettMinY;
        }
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
//        this.ammo.forEach(bullet -> bullet.drawImage(g));
//        g2d.setColor(Color.RED);
//        g2d.drawRect(x,y, hitBox.width, hitBox.height);
        if (speedBoostActive) {
            if (speedPowerUpTimer > 200) {
                g2d.drawImage(Resource.getResourceImage("hudSpeedPowerUp"),this.x, this.y + 20, null);
            } else {
                g2d.drawImage(Resource.getResourceImage("hudSpeedPowerUp2"),this.x, this.y + 20, null);
            }

//            g2d.drawString(String.valueOf(this.speedPowerUpTimer),this.x + 30, this.y + 20);


        }
    }

    public void update() {
        reloadTimer++;
        timeSinceLastRam++;
        if (speedBoostActive && speedPowerUpTimer > 0) {
            speedPowerUpTimer--;
        } else if (speedBoostActive && speedPowerUpTimer == 0) {
            speedBoostActive = false;
            this.setR(this.R - 1);
        }
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && reloadTimer > 20) {
            Bullet b = new Bullet(x,y,angle, Resource.getResourceImage("bullet"),this);
            this.tankGame.addBullet(b);
            this.ammo.add(b);
            this.reloadTimer = 0;
        }
        this.ammo.forEach(bullet -> bullet.update());
    }

    public void collision(Class objectInGame) {
        if (objectInGame.equals(Tank.class)) {
            this.x = this.prevX;
            this.y = this.prevY;
            this.vx = 0;
            this.vy = 0;
            if (timeSinceLastRam > 100) {
                this.health -= 10;
                this.timeSinceLastRam = 0;
            }

        } else if (objectInGame.equals(Bullet.class)) {
            this.health = this.health - 15;
        } else if (objectInGame.equals(HealthPowerUp.class)) {
            if (this.health + 25 <= 100) {
                this.health = this.health + 25;
            } else {
                this.health = 100;
            }
        } else if (objectInGame.equals(SpeedPowerUp.class)) {
//            System.out.println("Collision between tank and speed powerup");
            this.setR(this.R+1);
            this.speedPowerUpTimer = 500;
            this.speedBoostActive = true;
        } else if(objectInGame.equals(LivesPowerUp.class)) {
            if (this.lives < 4) {
                this.lives++;
            }
        }
        if (health <= 0) {
            this.lives--;
            this.health = 100;
            this.vx = 0;
            this.vy = 0;
        }


    }


    @Override
    public String toString() {
        return "Tank{" +
                "id='" + id + '\'' +
                '}';
    }
}
