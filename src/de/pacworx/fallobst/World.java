package de.pacworx.fallobst;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class World {
  public static final int WIDTH = 480;
  public static final int HEIGHT = 720;

  public float accelSpeed = 25; //settings for easy
  public float maxFallingSpeed = -150; //settings for easy
  public float gameSpeed = 1;

  private Random random = new Random();
  public Array<Vector2> spawningPositions = new Array<Vector2>(false, 35);
  public List<Vector2> staticApples = new ArrayList<Vector2>(10);
  public Array<Apple> apples = new Array<Apple>(false, 35);
  public Array<Bonus> bonuses = new Array<Bonus>(false, 10);
  private Array<Bonus> bonusPool = new Array<Bonus>(false, 10);
  public int applesCollected = 0;
  private float gameTime = 0f;

  public World() {
    createSpawningPositions();
    initApples();
  }

  public void update(float delta, float accel) {
    gameTime += delta;
    for (int i = 0; i < bonuses.size; i++) {
      Bonus bonus = bonuses.get(i);
      bonus.update(delta);
      if (bonus.state == Bonus.STATE_FINISHED) {
        bonuses.removeIndex(i);
        i--;
        returnBonusToPool(bonus);
      }
    }
    for (Apple apple : apples) {
      apple.update(delta, accel, gameSpeed);
      if ((apple.state == Apple.STATE_FALLING) && (apple.position.y <= 0)) {
        catchedAnApple(apple);
      }
    }
  }

  private void catchedAnApple(Apple apple) {
    letAnAppleShakle();

    Bonus bonus = getBonusFromPool();
    bonuses.add(bonus);
    bonus.spawn(apple.position.x, apple.position.y);
    applesCollected++;
    gameSpeed += 0.2f;

    spawningPositions.add(apple.originalPosition);
    apple.spawn(getRandomPosition(), accelSpeed, maxFallingSpeed);
  }

  private void letAnAppleShakle() {
    while (true) {
      Apple apple = apples.random();
      if (apple.state == Apple.STATE_READY) {
        apple.state = Apple.STATE_SHAKLING;
        return;
      }
    }
  }

  private Bonus getBonusFromPool() {
    if (bonusPool.size == 0) {
      return new Bonus();
    }
    return bonusPool.removeIndex(bonusPool.size - 1);
  }

  private void returnBonusToPool(Bonus bonus) {
    bonusPool.add(bonus);
  }

  private Vector2 getRandomPosition() {
    return spawningPositions.removeIndex(random.nextInt(spawningPositions.size));
  }

  private void initApples() {
    for (int i = 0; i < 20; i++) {
      Apple apple = new Apple();
      if (i == 0) {
        apple.spawn(getRandomPosition(), accelSpeed, maxFallingSpeed);
      } else {
        int state = (i == 1) ? Apple.STATE_FALLING : Apple.STATE_READY;
        apple.spawnInState(getRandomPosition(), accelSpeed, maxFallingSpeed, state);
      }
      apples.add(apple);
    }
  }

  private void createSpawningPositions() {
    spawningPositions.add(new Vector2(250, 420));
    spawningPositions.add(new Vector2(170, 650));
    spawningPositions.add(new Vector2(410, 460));
    spawningPositions.add(new Vector2(50, 395));
    spawningPositions.add(new Vector2(200, 520));
    spawningPositions.add(new Vector2(220, 360));
    spawningPositions.add(new Vector2(360, 500));
    spawningPositions.add(new Vector2(140, 600));
    spawningPositions.add(new Vector2(320, 575));
    spawningPositions.add(new Vector2(100, 550));
    spawningPositions.add(new Vector2(310, 530));
    spawningPositions.add(new Vector2(260, 480));
    spawningPositions.add(new Vector2(200, 400));
    spawningPositions.add(new Vector2(230, 580));
    spawningPositions.add(new Vector2(120, 400));
    spawningPositions.add(new Vector2(280, 360));
    spawningPositions.add(new Vector2(130, 455));
    spawningPositions.add(new Vector2(330, 460));
    spawningPositions.add(new Vector2(140, 485));
    spawningPositions.add(new Vector2(355, 370));
    spawningPositions.add(new Vector2(320, 390));
    spawningPositions.add(new Vector2(275, 540));
    spawningPositions.add(new Vector2(80, 485));
    spawningPositions.add(new Vector2(360, 500));
    spawningPositions.add(new Vector2(380, 390));
    spawningPositions.add(new Vector2(230, 500));
    spawningPositions.add(new Vector2(190, 600));
    spawningPositions.add(new Vector2(265, 610));
    spawningPositions.add(new Vector2(225, 450));
    spawningPositions.add(new Vector2(285, 430));
    spawningPositions.add(new Vector2(150, 560));
    spawningPositions.add(new Vector2(185, 430));
    spawningPositions.add(new Vector2(200, 470));
    spawningPositions.add(new Vector2(380, 435));
    spawningPositions.add(new Vector2(380, 540));

    staticApples.add(new Vector2(350, 330));
    staticApples.add(new Vector2(100, 330));
    staticApples.add(new Vector2(125, 225));
    staticApples.add(new Vector2(300, 235));
    staticApples.add(new Vector2(365, 245));
    staticApples.add(new Vector2(430, 325));
    staticApples.add(new Vector2(340, 290));
    staticApples.add(new Vector2(300, 300));
    staticApples.add(new Vector2(70, 270));
    staticApples.add(new Vector2(400, 270));
  }
}
