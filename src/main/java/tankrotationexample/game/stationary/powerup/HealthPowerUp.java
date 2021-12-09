package tankrotationexample.game.stationary.powerup;

import tankrotationexample.game.movable.Tank;
import tankrotationexample.game.stationary.powerup.PowerUp;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends PowerUp {


    String id = "healthPowerUp";


    public HealthPowerUp(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        this.active = true;
        this.img = image;
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
    }
//    public void drawImage(Graphics g) {
//        if (state == 1)
//        {
//            Graphics2D g2 = (Graphics2D) g;
//            g2.drawImage(this.img,x,y,null);
//        }
//
//    }

    @Override
    public void update() {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Rectangle getBounds() {
        return this.hitBox.getBounds();
    }

    @Override
    public void collision(Class objectInGame) {
        if (objectInGame.equals(Tank.class)) {
            this.active = false;
        }
    }

    @Override
    public String toString() {
        return "HealthPowerUp{" +
                "id='" + id + '\'' +
                '}';
    }
}
