package tankrotationexample.game.stationary.wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnBreakWall extends Wall {
    @Override
    public String getId() {
        return id;
    }



    String id = "unBreakWall";

    public UnBreakWall(int x, int y, BufferedImage wallImage) {
        this.x = x;
        this.y = y;
        this.img = wallImage;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.active = true;
    }


    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,x,y,null);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return this.hitBox.getBounds();
    }

    @Override
    public void collision(Class objectInGame) {

    }

    @Override
    public String toString() {
        return "UnBreakWall{" +
                "x='" + x + '\'' + "y='" + y + '\'' +
                '}';
    }
}
