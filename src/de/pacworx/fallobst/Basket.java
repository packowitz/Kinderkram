package de.pacworx.fallobst;

import com.badlogic.gdx.math.Vector2;


public class Basket {
  public static final float SIZE = 120;
  private final float Y_BOUND = SIZE * 2 / 3;
  public final Vector2 position = new Vector2();


  public Basket(float x, float y) {
    position.x = x;
    position.y = y;
  }

  public boolean isInBasket(Vector2 point) {
    if ((point.x >= position.x) && (point.x <= (position.x + SIZE)) &&
        (point.y >= position.y) && (point.y <= (position.y + Y_BOUND))) {
      return true;
    }
    return false;
  }
}
