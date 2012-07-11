package de.pacworx.fallobst;

import com.badlogic.gdx.math.Vector2;


public class MovingObject {
  public static final int STATE_MOVING = 0;
  public static final int STATE_ARRIVED = 1;
  public int state;
  Vector2 start;
  Vector2 position;
  Vector2 velocity;
  float lifetime = 0;
  float inTime;

  public MovingObject(float startX, float startY, float targetX, float targetY, float inTime) {
    start = new Vector2(startX, startY);
    position = new Vector2(startX, startY);
    this.inTime = inTime;
    state = STATE_MOVING;
    calcVelocity(targetX, targetY);
  }

  private void calcVelocity(float targetX, float targetY) {
    velocity = new Vector2(targetX, targetY);
    velocity.sub(start);
    velocity.mul(1 / inTime);
  }

  public void update(float delta) {
    lifetime += delta;
    if (lifetime >= inTime) {
      lifetime = inTime;
      state = STATE_ARRIVED;
    }
    position.set(start.x + (velocity.x * lifetime), start.y + (velocity.y * lifetime));
  }
}
