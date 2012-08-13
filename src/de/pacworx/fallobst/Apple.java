package de.pacworx.fallobst;

import com.badlogic.gdx.math.Vector2;
import de.pacworx.Difficulty;
import de.pacworx.Settings;


public class Apple {
  public static final int STATE_GROWING = 0;
  public static final int STATE_READY = 1;
  public static final int STATE_SHAKLING = 2;
  public static final int STATE_FALLING = 3;
  public static final int GRAVITY = -40;
  public static final float APPLE_SIZE = 32;
  public static final float GROWING_SPEED = APPLE_SIZE / 6;
  public static final float SHAKLE_TIME = 1.2f;
  public static final float SHAKLE_SPEED = 80;
  public static final float SHAKLE_MAX_OFFSET = 1.2f;

  public Vector2 originalPosition;
  public final Vector2 position = new Vector2();
  public final Vector2 velocity = new Vector2();
  private float accelSpeed;
  private float maxFallingSpeed;
  public float size;
  public int state;
  public float stateTime;
  public float shakleOffset;
  public int shakleDir = 1;
  public boolean isRotten = false;

  public void spawn(Vector2 position, float accelSpeed, float maxFallingSpeed) {
    originalPosition = position;
    this.position.x = position.x;
    this.position.y = position.y;
    velocity.x = 0;
    velocity.y = 0;
    this.accelSpeed = accelSpeed;
    this.maxFallingSpeed = maxFallingSpeed;
    size = 0;
    state = STATE_GROWING;
    stateTime = 0;
    shakleOffset = 0;
    if (((Settings.getDifficulty() == Difficulty.SCHULE_1_2) || (Settings.getDifficulty() == Difficulty.SCHULE_3_4)) &&
        (World.random.nextFloat() < 0.35)) {
      isRotten = true;
    } else {
      isRotten = false;
    }
  }

  public void spawnInState(Vector2 position, float accelSpeed, float maxFallingSpeed, int state) {
    this.spawn(position, accelSpeed, maxFallingSpeed);
    this.state = state;
    size = APPLE_SIZE;
  }

  public void update(float delta, float accel, float gameSpeed) {
    switch (state) {
      case STATE_GROWING: {
        size += GROWING_SPEED * delta;
        if (size >= APPLE_SIZE) {
          size = APPLE_SIZE;
          state = STATE_READY;
        }
        break;
      }

      case STATE_SHAKLING: {
        stateTime += delta;
        if (stateTime >= SHAKLE_TIME) {
          stateTime = 0;
          state = STATE_FALLING;
          position.x += shakleOffset;
          shakleOffset = 0;
        } else {
          shakleOffset += shakleDir * delta * SHAKLE_SPEED;
          if ((shakleDir * shakleOffset) >= SHAKLE_MAX_OFFSET) {
            shakleDir *= -1;
          }
        }
        break;
      }

      case STATE_FALLING: {
        if (velocity.y > (maxFallingSpeed * gameSpeed)) {
          velocity.add(0, GRAVITY * gameSpeed * delta);
        }
        velocity.x = -accel * accelSpeed;
        position.add(velocity.x * delta, velocity.y * delta);
        if (position.x < (size / 2)) {
          position.x = size / 2;
        }
        if (position.x > (World.WIDTH - (size / 2))) {
          position.x = World.WIDTH - (size / 2);
        }
        break;
      }
    }
  }
}
