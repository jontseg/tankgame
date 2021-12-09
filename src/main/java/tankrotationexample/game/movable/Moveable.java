package tankrotationexample.game.movable;

import tankrotationexample.GameConstants;
import tankrotationexample.game.controllers.GameObject;

public abstract class Moveable extends GameObject {


    public Moveable(int  x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }


}
