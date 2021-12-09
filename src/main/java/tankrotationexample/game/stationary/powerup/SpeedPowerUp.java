package tankrotationexample.game.stationary.powerup;

import tankrotationexample.game.controllers.Resource;
import tankrotationexample.game.movable.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedPowerUp extends PowerUp {
    @Override
    public String getId() {
        return id;
    }



    String id = "speedPowerUp";


    public SpeedPowerUp(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.angle = 0;
        this.active = true;
        this.img = Resource.getResourceImage("speedPowerUp");
        this.hitBox = new Rectangle(x,y,this.img.getWidth(),this.img.getHeight());
    }

//    public void drawImage(Graphics g) {
//        if (state == 1)
//        {
//            Graphics2D g2 = (Graphics2D) g;
//            g2.drawImage(this.wallImage,x,y,null);
//        }
//
//    }

    @Override
    public void update() {

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
        return "SpeedPowerUp{" +
                "id='" + id + '\'' +
                '}';
    }
}
