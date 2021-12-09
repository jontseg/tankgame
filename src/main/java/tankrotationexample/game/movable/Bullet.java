package tankrotationexample.game.movable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tankrotationexample.game.controllers.Resource;
import tankrotationexample.game.stationary.powerup.PowerUp;

public class Bullet extends Moveable {
    public String getId() {
        return id;
    }



    String id = "bullet";

    public void setR(int r) {
        R = r;
    }

    int R = 7;
    Rectangle hitBox;

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    private int prevX;
    private int prevY;


    public boolean hasCollided() {
        return hasCollided;
    }

    private boolean hasCollided;
    private int timeSinceImpact = 0;

    public Tank getShooter() {
        return shooter;
    }

    private Tank shooter;

    public Bullet(int x, int y, int angle, BufferedImage bulletImage, Tank tank) {
        super(x, y, angle);

        this.img = bulletImage;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
        this.active = true;
        this.shooter = tank;
    }

    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        prevX = x;
        prevY = y;
        x += vx;
        y += vy;
        this.hitBox.setLocation(x,y);
//        checkBorder();
    }



    public void update() {
        moveForwards();
        if (timeSinceImpact >= 4){
            active = false;
        }
        if (hasCollided) {
            timeSinceImpact++;
        }

    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
//        g2d.setColor(Color.RED);
//        g2d.drawRect(x,y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public Rectangle getBounds() {
        return this.hitBox.getBounds();
    }

    @Override
    public void collision(Class objectInGame) {
        // if bullet collides with anything besides the powerups, bullet stops moving and is no longer active

        if (!(objectInGame.getClass().equals(PowerUp.class))) {
            this.hasCollided = true;
            this.vx = 0;
            this.vy = 0;
//            this.R = 0;
            if (objectInGame.getClass().equals(Tank.class)) {
                this.img = Resource.getResourceImage("explode2");
            } else {
                this.img = Resource.getResourceImage("explode1");
            }
        }
    }


    @Override
    public String toString() {
        return "Bullet{" +
                "x='" + x + '\'' + "y='" + y + '\'' +
                '}';
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVx(int x) {
        this.vx = x;
    }

    public void setVy(int y) {
        this.vy = y;
    }
}
