package tankrotationexample.game.stationary.wall;

import tankrotationexample.game.controllers.Resource;
import tankrotationexample.game.movable.Bullet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakWall extends Wall {


    String id = "breakWall";
    int wallHealth = 3;



    public BreakWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.img = wallImage;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.active = true;
    }


    public void drawImage(Graphics g) {
        if (wallHealth >= 1)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.img,x,y,null);
        }

    }

    @Override
    public void update() {
        if (wallHealth < 3) {
            this.img = Resource.getResourceImage("break2");
        }
    }

    public String getId() {
        return this.id;
    }

    @Override
    public Rectangle getBounds() {
        return this.hitBox.getBounds();
    }

    @Override
    public void collision(Class objectInGame) {
        if (objectInGame.equals(Bullet.class)) {
            this.wallHealth -= 1;
        }

        if (wallHealth <= 0) {
            this.active = false;
        }
    }


    @Override
    public String toString() {
        return "BreakWall{" +
                "id='" + id + '\'' +
                '}';
    }
}
