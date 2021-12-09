/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.game.controllers.Collision;
import tankrotationexample.game.controllers.GameObject;
import tankrotationexample.game.controllers.Resource;
import tankrotationexample.game.controllers.TankControl;
import tankrotationexample.game.movable.*;
import tankrotationexample.game.stationary.powerup.HealthPowerUp;
import tankrotationexample.game.stationary.powerup.LivesPowerUp;
import tankrotationexample.game.stationary.powerup.SpeedPowerUp;
import tankrotationexample.game.stationary.wall.BreakWall;
import tankrotationexample.game.stationary.wall.UnBreakWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
//    public static BufferedImage bulletImage;
    private JFrame jFrame;

    ArrayList<Tank> tanks; // add this later
    private Launcher lf;
    private Tank tankOne;
    private Tank tankTwo;
    private Collision collision;
    private GameObject obj1;
    private GameObject obj2;


    static long tick = 0;
    ArrayList<GameObject> gameObjects;
//    ArrayList<Wall> walls;
//    ArrayList<PowerUp> powerups;


    public long getTick() {
        return tick;
    }
    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
        obj1 =  new BreakWall(0,0, Resource.getResourceImage("break"));
        obj2 = new BreakWall(0,0,Resource.getResourceImage("break"));
        collision = new Collision(obj1,obj2);
       try {
           this.resetGame();
           while (this.tankOne.getLives() >0 && this.tankTwo.getLives() > 0) {
               this.tick++;
               int i = 0;
               while (i < gameObjects.size()) {
                   this.gameObjects.get(i).update();
                   if (!(this.gameObjects.get(i).isActive())) {
                       gameObjects.remove(this.gameObjects.get(i));
                   } else {
                       i++;
                   }
               }

               for ( i = 0; i < gameObjects.size(); i++) {
                   for (int j = i; j < gameObjects.size(); j++) {
                       obj1 = gameObjects.get(i);
                       obj2 = gameObjects.get(j);
                       collision.setObj1(obj1);
                       collision.setObj2(obj2);
                       collision.checkCollision();
                   }
               }

               this.repaint();   // redraw game

                Thread.sleep(1000 / 144); //sleep for a few milliseconds

            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;

        this.tankOne.setX(300);
        this.tankOne.setY(300);
        this.tankOne.resetBounds();
        this.tankTwo.setX(1500);
        this.tankTwo.setY(500);
        this.tankTwo.resetBounds();
    }

    public void addBullet(Bullet bullet) {
        gameObjects.add(bullet);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.jFrame = new JFrame("Tank Rotation");
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                                       GameConstants.GAME_WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        gameObjects = new ArrayList<>();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            InputStreamReader isr = new InputStreamReader(Launcher.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            String[]  mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    switch(mapInfo[curCol]) {
                        case "2":
//                            this.walls.add(new BreakWall(curCol * 30, curRow*30,breakWall));
                            this.gameObjects.add(new BreakWall(curCol * 30, curRow*30,Resource.getResourceImage("break")));
                            break;
                        case "4":
//                            this.powerups.add(new HealthPowerUp(curCol * 30, curRow*30, healthPowerUp));
                            this.gameObjects.add(new HealthPowerUp(curCol * 30, curRow*30, Resource.getResourceImage("healthPowerUp")));
                            break;
                        case "5":
//                            this.powerups.add(new SpeedPowerUp(curCol * 30, curRow*30, speedPowerUp));
                            this.gameObjects.add(new SpeedPowerUp(curCol * 30, curRow*30, Resource.getResourceImage("speedPowerUp")));
                            break;
                        case "6":
//                            this.powerups.add(new SpeedPowerUp(curCol * 30, curRow*30, speedPowerUp));
                            this.gameObjects.add(new LivesPowerUp(curCol * 30, curRow*30, Resource.getResourceImage("livesPowerUp")));
                            break;
                        case "3":
                        case "9":
//                            this.walls.add(new UnBreakWall(curCol * 30, curRow*30, unBreakWall));
                            this.gameObjects.add(new UnBreakWall(curCol * 30, curRow*30, Resource.getResourceImage("unbreak")));


                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        tankOne = new Tank(300, 500, 0, 0, 0, Resource.getResourceImage("tank1"),this,1);
        tankTwo = new Tank(1500, 500, 0, 0, 0, Resource.getResourceImage("tank2"), this,2);
        this.gameObjects.add(tankOne);
        this.gameObjects.add(tankTwo);
        TankControl tankControlOne = new TankControl(tankOne, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tankControlTwo = new TankControl(tankTwo, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tankControlOne);
        this.lf.getJf().addKeyListener(tankControlTwo);
//        this.jFrame.setLayout(new BorderLayout());
//        this.jFrame.add(this);
//        this.jFrame.addKeyListener(tankControlOne);
//        this.jFrame.addKeyListener(tankControlTwo);
//        this.jFrame.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
//        this.jFrame.setResizable(false);
//        jFrame.setLocationRelativeTo(null);
//        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.jFrame.setVisible(true);


    }



    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 10.0F);
        if (this.tankOne.getLives() > 0 && this.tankTwo.getLives() > 0) {
            buffer.setColor(Color.BLACK); // Draws black background to remove trail
            buffer.fillRect(0,0,GameConstants.GAME_WORLD_WIDTH,GameConstants.GAME_WORLD_HEIGHT);
            this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));
//        Tank t1 = (Tank) gameObjects.get(gameObjects.size() -2 );
//        Tank t2 = (Tank) gameObjects.get(gameObjects.size() -1 );
//        this.walls.forEach(wall -> wall.drawImage(buffer));
//        this.powerups.forEach(powerUp -> powerUp.drawImage(buffer));
//        this.tankOne.drawImage(buffer);
//        this.tankTwo.drawImage(buffer);
            BufferedImage leftHalf = world.getSubimage(this.tankOne.getCamX(),this.tankOne.getCamY(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
//        BufferedImage rightHalf = world.getSubimage(tankTwo.getX(),tankTwo.getY(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
            BufferedImage rightHalf = world.getSubimage(this.tankTwo.getCamX(),this.tankTwo.getCamY(),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
            BufferedImage mm = world.getSubimage(0,0,GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
            g2.drawImage(leftHalf,0,0,null);
            g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 +4,0,null);
            g2.scale(.15,.15);
            g2.drawImage(mm.getScaledInstance( GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_WIDTH , BufferedImage.TYPE_INT_RGB), GameConstants.GAME_SCREEN_WIDTH * 3 - 30, 0, null);

            // lives and health tank 1

            g2.setFont(newFont);
            g2.setColor(Color.GREEN);

            for (int i = 0; i < this.tankOne.getLives()  && i <= 4; i++) {
                g2.drawImage(Resource.getResourceImage("hudlives"),GameConstants.GAME_SCREEN_WIDTH/2 + i * 100,GameConstants.GAME_WORLD_HEIGHT/4, null);
            }
            g2.drawString("Health: " + this.tankOne.getHealth(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_WORLD_HEIGHT/2 - 200);
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH/2 , GameConstants.GAME_WORLD_HEIGHT/2 - 100, 1500, 48);
            g2.setColor(Color.GREEN);
            for (int i = 0; i < this.tankOne.getHealth() && i < 100; i++) {
                g2.drawRect(GameConstants.GAME_SCREEN_WIDTH/2 + i *15, GameConstants.GAME_WORLD_HEIGHT/2 - 100, 15, 46);
                g2.fillRect(GameConstants.GAME_SCREEN_WIDTH/2 + i *15,GameConstants.GAME_WORLD_HEIGHT/2 - 100, 15,40);
            }

            // lives and health tank 2
            for (int i = 0; i < this.tankTwo.getLives()  && i <= 4; i++) {
                g2.drawImage(Resource.getResourceImage("hudlives"),GameConstants.GAME_SCREEN_WIDTH * 4 + i * 100,GameConstants.GAME_WORLD_HEIGHT/4, null);
            }
            g2.drawString("Health: " + this.tankTwo.getHealth(), GameConstants.GAME_SCREEN_WIDTH * 4, GameConstants.GAME_WORLD_HEIGHT/2 - 200);
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 4 , GameConstants.GAME_WORLD_HEIGHT/2 - 100, 1500, 48);
            g2.setColor(Color.GREEN);
            for (int i = 0; i < this.tankTwo.getHealth() && i < 100; i++) {
                g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 4 + i *15, GameConstants.GAME_WORLD_HEIGHT/2 - 100, 15, 46);
                g2.fillRect(GameConstants.GAME_SCREEN_WIDTH * 4 + i *15,GameConstants.GAME_WORLD_HEIGHT/2 - 100, 15,40);
            }

        } else if (this.tankOne.getLives() > 0) {
            gameInitialize();
            resetGame();
            this.lf.setFrame("end");


        } else {
            gameInitialize();
            resetGame();
            this.lf.setFrame("end");
        }

    }

}
