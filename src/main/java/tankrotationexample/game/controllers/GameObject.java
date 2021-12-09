package tankrotationexample.game.controllers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public abstract class GameObject {

    public int x;
    public int y;



    public int vx;
    public int vy;
    public int angle;
    public Boolean active;
    public String id;
    public BufferedImage img;
    public Rectangle hitBox;



    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
    public abstract void update(); // probably shouldnt be here
    public abstract String getId();
    public abstract Rectangle getBounds();
    public abstract void collision(Class objectInGame);
    public boolean isActive() {
        return this.active;
    }

    @Override
    public abstract String toString();
}
