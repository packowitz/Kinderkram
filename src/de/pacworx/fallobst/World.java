package de.pacworx.fallobst;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.pacworx.Difficulty;
import de.pacworx.Settings;
import de.pacworx.kinderkram.Highscore;


public class World {
  public static final int WIDTH = 480;
  public static final int HEIGHT = 720;
  public static final int STATE_LIVING = 0;
  public static final int STATE_GAME_OVER = 1;
  public static final int STATE_PAUSED = 2;

  public float accelSpeed;
  public float maxFallingSpeed;
  public float gameSpeed;
  public int state = STATE_LIVING;

  private Highscore highscore;
  public static Random random = new Random();
  public Array<Vector2> spawningPositions = new Array<Vector2>(false, 35);
  public List<Vector2> staticApples = new ArrayList<Vector2>(10);
  public Array<Apple> apples = new Array<Apple>(false, 35);
  public Array<Bonus> bonuses = new Array<Bonus>(false, 10);
  private Array<Bonus> bonusPool = new Array<Bonus>(false, 10);
  public Basket basket = new Basket();
  public int applesCollected = 0;
  public int highscorePosition = 0;

  public World(Highscore highscore) {
    this.highscore = highscore;
    if ((Settings.getDifficulty() == Difficulty.KINDERGARTEN) || (Settings.getDifficulty() == Difficulty.VORSCHULE)) {
      accelSpeed = 30;
      maxFallingSpeed = -150;
      gameSpeed = 1;
    } else {
      accelSpeed = 45;
      maxFallingSpeed = -300;
      gameSpeed = 2f;
    }
    createSpawningPositions();
    initApples();
  }

  public void pause() {
    if (state != STATE_GAME_OVER) {
      state = STATE_PAUSED;
    }
  }

  public void resume() {
    if (state == STATE_PAUSED) {
      state = STATE_LIVING;
    }
  }

  public void update(float delta, float accel) {
    if (delta > 0.04) {
      delta = 0.04f;
    }
    basket.update(delta);
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
      if (apple.isRotten) {
        if (basket.isInBasket(apple.position)) {
          finishGame();
        }
        if ((apple.state == Apple.STATE_FALLING) && (apple.position.y <= 0)) {
          catchedAnApple(apple);
        }
      } else {
        if (basket.isInBasket(apple.position)) {
          catchedAnApple(apple);
        }
        if ((apple.state == Apple.STATE_FALLING) && (apple.position.y <= 0)) {
          finishGame();
        }
      }
    }
  }

  private void catchedAnApple(Apple apple) {
    letAnAppleShakle();

    Bonus bonus = getBonusFromPool();
    bonuses.add(bonus);
    bonus.spawn(apple.position.x, apple.position.y);
    applesCollected++;
    gameSpeed += 0.4f;

    spawningPositions.add(apple.originalPosition);
    apple.spawn(getRandomPosition(), accelSpeed, maxFallingSpeed);
  }

  private void finishGame() {
    this.state = STATE_GAME_OVER;

    highscorePosition = highscore.insertScore(applesCollected);

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
        int state = (i == 1) ? Apple.STATE_SHAKLING : Apple.STATE_READY;
        apple.spawnInState(getRandomPosition(), accelSpeed, maxFallingSpeed, state);
      }
      apples.add(apple);
    }
  }

  private void createSpawningPositions() {
    spawningPositions.add(new Vector2(50, 395));
    spawningPositions.add(new Vector2(80, 485));
    spawningPositions.add(new Vector2(100, 550));
    spawningPositions.add(new Vector2(120, 400));
    spawningPositions.add(new Vector2(130, 455));
    spawningPositions.add(new Vector2(140, 485));
    spawningPositions.add(new Vector2(145, 600));
    spawningPositions.add(new Vector2(150, 560));
    spawningPositions.add(new Vector2(170, 650));
    spawningPositions.add(new Vector2(185, 430));
    spawningPositions.add(new Vector2(190, 600));
    spawningPositions.add(new Vector2(195, 400));
    spawningPositions.add(new Vector2(200, 470));
    spawningPositions.add(new Vector2(205, 545));
    spawningPositions.add(new Vector2(210, 510));
    spawningPositions.add(new Vector2(215, 360));
    spawningPositions.add(new Vector2(225, 430));
    spawningPositions.add(new Vector2(230, 580));
    spawningPositions.add(new Vector2(250, 400));
    spawningPositions.add(new Vector2(260, 480));
    spawningPositions.add(new Vector2(265, 610));
    spawningPositions.add(new Vector2(275, 540));
    spawningPositions.add(new Vector2(280, 360));
    spawningPositions.add(new Vector2(285, 430));
    spawningPositions.add(new Vector2(310, 530));
    spawningPositions.add(new Vector2(315, 390));
    spawningPositions.add(new Vector2(320, 575));
    spawningPositions.add(new Vector2(330, 460));
    spawningPositions.add(new Vector2(355, 370));
    spawningPositions.add(new Vector2(360, 400));
    spawningPositions.add(new Vector2(365, 500));
    spawningPositions.add(new Vector2(380, 435));
    spawningPositions.add(new Vector2(390, 540));
    spawningPositions.add(new Vector2(405, 390));
    spawningPositions.add(new Vector2(410, 460));

    staticApples.add(new Vector2(352, 330));
    staticApples.add(new Vector2(102, 330));
    staticApples.add(new Vector2(127, 225));
    staticApples.add(new Vector2(302, 235));
    staticApples.add(new Vector2(363, 245));
    staticApples.add(new Vector2(432, 325));
    staticApples.add(new Vector2(342, 290));
    staticApples.add(new Vector2(302, 300));
    staticApples.add(new Vector2(72, 270));
    staticApples.add(new Vector2(402, 270));
  }
}
