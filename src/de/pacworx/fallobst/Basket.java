package de.pacworx.fallobst;

import com.badlogic.gdx.math.Vector2;
import de.pacworx.Difficulty;
import de.pacworx.Settings;


public class Basket {
  public static final float SIZE = 100;
  private static final float MOVE_MIN = 0;
  private static final float MOVE_MAX = World.WIDTH - SIZE - MOVE_MIN;
  private static final float MOVING_SPEED = 100;
  private static final float Y_BOUND = SIZE * 2 / 3;
  public final Vector2 position = new Vector2();
  private final boolean moving;
  private float movingDir = -1;


  public Basket() {
    position.x = World.WIDTH / 2;
    position.y = 0;
    moving = (Settings.getDifficulty() == Difficulty.VORSCHULE) || (Settings.getDifficulty() == Difficulty.SCHULE_3_4);
  }

  public void update(float delta) {
    if (moving) {
      position.x += movingDir * delta * MOVING_SPEED;
      if ((movingDir == -1) && (position.x <= MOVE_MIN)) {
        movingDir = 1;
      } else if ((movingDir == 1) && (position.x >= MOVE_MAX)) {
        movingDir = -1;
      }
    }
  }

  public boolean isInBasket(Vector2 point) {
    if ((point.x >= position.x) && (point.x <= (position.x + SIZE)) &&
        (point.y >= position.y) && (point.y <= (position.y + Y_BOUND))) {
      return true;
    }
    return false;
  }
}
