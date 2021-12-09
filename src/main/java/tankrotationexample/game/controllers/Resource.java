package tankrotationexample.game.controllers;

import tankrotationexample.game.TRE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.imageio.ImageIO.read;

public class Resource {
    private static Map<String, BufferedImage> resources;

    static {
        Resource.resources = new HashMap<>();
        try {
            Resource.resources.put("tank1",read(TRE.class.getClassLoader().getResource("tank1.png")));
            Resource.resources.put("tank2",read(TRE.class.getClassLoader().getResource("tank2.png")));
            Resource.resources.put("unbreak",read(TRE.class.getClassLoader().getResource("unbreak.png")));
            Resource.resources.put("break",read(TRE.class.getClassLoader().getResource("break.png")));
            Resource.resources.put("break2",read(TRE.class.getClassLoader().getResource("break2.png")));
            Resource.resources.put("healthPowerUp",read(TRE.class.getClassLoader().getResource("healthpowerup.png")));
            Resource.resources.put("speedPowerUp",read(TRE.class.getClassLoader().getResource("speedpowerup.png")));
            Resource.resources.put("livesPowerUp",read(TRE.class.getClassLoader().getResource("livespowerup.png")));
            Resource.resources.put("bullet",read(TRE.class.getClassLoader().getResource("bullet.png")));
            Resource.resources.put("explode1",read(TRE.class.getClassLoader().getResource("explode1.png")));
            Resource.resources.put("explode2",read(TRE.class.getClassLoader().getResource("explode2.png")));
            Resource.resources.put("lives",read(TRE.class.getClassLoader().getResource("lives.png")));
            Resource.resources.put("hudlives",read(TRE.class.getClassLoader().getResource("hudlives.png")));
            Resource.resources.put("hudSpeedPowerUp",read(TRE.class.getClassLoader().getResource("hudspeedboost.png")));
            Resource.resources.put("hudSpeedPowerUp2",read(TRE.class.getClassLoader().getResource("hudspeedboost2.png")));


        } catch(IOException e) {
            e.printStackTrace();
            System.exit(-5);
        }


    }

    public static BufferedImage getResourceImage(String key) {
        return Resource.resources.get(key);
    }
}
