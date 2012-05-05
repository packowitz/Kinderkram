package de.pacworx.fallobst;

import com.badlogic.gdx.math.Vector2;


public class Bonus {
  public static final int STATE_VISIBLE = 0;
  public static final int STATE_FINISHED = 1;
  public static final float VISIBLE_TIME = 2f;

  public final Vector2 position = new Vector2();
  public final int speed = 30;
  public int state;
  private float stateTime;

  public void spawn(float x, float y) {
    position.x = x;
    position.y = y;
    state = STATE_VISIBLE;
    stateTime = 0;
  }

  public void update(float delta) {
    stateTime += delta;
    if (stateTime >= VISIBLE_TIME) {
      state = STATE_FINISHED;
    } else {
      position.add(0, speed * delta);
    }
  }
}
