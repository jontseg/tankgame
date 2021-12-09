package tankrotationexample.game.controllers;


import java.awt.*;

import tankrotationexample.game.movable.*;
import tankrotationexample.game.stationary.powerup.PowerUp;
import tankrotationexample.game.stationary.wall.Wall;

public class Collision {


    private GameObject obj1, obj2;


    public Collision(GameObject obj1, GameObject obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public void setObj1(GameObject obj1) {
        this.obj1 = obj1;
    }

    public void setObj2(GameObject obj2) {
        this.obj2 = obj2;
    }
    public void checkCollision() {
//        if (obj1 instanceof UnBreakWall || obj2 instanceof UnBreakWall) {
//            return;
//        }
        // ignore collisions between an object and itself
        if (obj1.equals(obj2)) {
            return;
        }

//         ignore collision between bullet and its shooter
        if ((((obj1 instanceof Bullet) && (obj2 instanceof Tank) && obj2.equals(((Bullet) obj1).getShooter()) || ((obj2 instanceof Bullet) && (obj1 instanceof Tank) && obj1.equals(((Bullet) obj2).getShooter()))))) {
            return;
        }

        if (obj1 instanceof Bullet && ((Bullet) obj1).hasCollided() || obj2 instanceof Bullet && ((Bullet) obj2).hasCollided()) {
            return;
        }
        Rectangle obj1hitBox = obj1.getBounds();
        Rectangle obj2hitBox = obj2.getBounds();
        if (obj1 instanceof Bullet || obj2 instanceof Bullet) {
            if (obj1hitBox.intersects(obj2hitBox)) {
//                System.out.print("Bullet Collision Occurred:");
//                System.out.println("Object 1: ");
//                System.out.print(obj1.toString());
//                System.out.println("Object 2: ");
//                System.out.print(obj2.toString());
//                System.out.println();
                obj1.collision(obj2.getClass());
                obj2.collision(obj1.getClass());
            }
        }
        if (obj1 instanceof PowerUp && obj2 instanceof Tank || obj2 instanceof PowerUp && obj1 instanceof Tank) {

            if (obj1hitBox.intersects(obj2hitBox))
            {
                obj1.collision(obj2.getClass());
                obj2.collision(obj1.getClass());
            }

        }

        // check if tank is gonna hit wall
        if (obj1 instanceof Tank && obj2 instanceof Wall) {
            checkCollisionBorder((Tank) obj1, (Wall) obj2);
        }
        if (obj1 instanceof Wall && obj2 instanceof Tank) {
            checkCollisionBorder((Tank) obj2, (Wall) obj1);
        }
        if (obj1 instanceof Tank && obj2 instanceof Tank && ((Tank) obj1).getPlayerNum() != ((Tank) obj2).getPlayerNum()) {
            if (obj1hitBox.intersects(obj2hitBox))
            {
                obj1.collision(obj2.getClass());
                obj2.collision(obj1.getClass());
            }
        }
    }

    private void checkCollisionBorder(Tank tank, Wall wall) {
        int lookDistance = 1;

        Rectangle tankHitBox = tank.getBounds();
        Rectangle wallHitBox = wall.getBounds();
        // look left
        tankHitBox.setBounds(tankHitBox.x - lookDistance, tankHitBox.y, tankHitBox.width, tankHitBox.height);
        if (tankHitBox.intersects(wallHitBox)) {
            tank.setX(tank.getPrevX());
            tank.setY(tank.getPrevY());
            tank.setVx(0);
            tank.setVy(0);

//            System.out.println("Collision with wall left");
        }
        // look right
        tankHitBox = tank.getBounds();
        tankHitBox.setBounds(tankHitBox.x + lookDistance, tankHitBox.y, tankHitBox.width, tankHitBox.height);
        if (tankHitBox.intersects(wallHitBox)) {
            tank.setX(tank.getPrevX());
            tank.setY(tank.getPrevY());
            tank.setVx(0);
            tank.setVy(0);
//            System.out.println("Collision with wall right");
//            System.out.println(tank.getCamX());
        }
        // look up
        tankHitBox = tank.getBounds();
        tankHitBox.setBounds(tankHitBox.x, tankHitBox.y - lookDistance, tankHitBox.width, tankHitBox.height);

        if (tankHitBox.intersects(wallHitBox)) {
            tank.setX(tank.getPrevX());
            tank.setY(tank.getPrevY());
            tank.setVx(0);
            tank.setVy(0);
//            System.out.println("Collision with wall up");
        }
        // look down
        tankHitBox = tank.getBounds();
        tankHitBox.setBounds(tankHitBox.x, tankHitBox.y + lookDistance, tankHitBox.width, tankHitBox.height);
        if (tankHitBox.intersects(wallHitBox)) {
            tank.setX(tank.getPrevX());
            tank.setY(tank.getPrevY());
            tank.setVx(0);
            tank.setVy(0);
//            System.out.println("Collision with wall down");
        }
    }



}

