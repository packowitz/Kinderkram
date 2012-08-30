package de.pacworx.farbkleckse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import de.pacworx.Difficulty;
import de.pacworx.Settings;


public class World implements InputProcessor {
  public static final int WIDTH = 480;
  public static final int HEIGHT = 720;
  public static final int STATE_LIVING = 0;
  public static final int STATE_GAME_OVER = 1;
  public static final int STATE_PAUSED = 2;

  private FarbkleckseGame game;
  private OrthographicCamera camera;
  private Random random = new Random();
  public int state = STATE_LIVING;

  public float time = 0;
  public float penaltyTime = 0;
  public int penaltySum = 0;
  private float timeForShuffle = 0;
  public int finishedPosition = 0;

  public int[] colorAreas = { 0, 1, 2, 3 };
  private List<Integer> colorShuffleList = new ArrayList<Integer>(4);
  private int colorRotateBuffer = 0;

  public int blobColor;
  public int blobX;
  public int blobY;
  public int successCounter = 20;

  private int touchPointerId = -1;
  private Vector3 touchPoint = new Vector3();
  private int touchOffsetX = 0;
  private int touchOffsetY = 0;
  private int touchInArea = -1;

  public World(FarbkleckseGame game, OrthographicCamera camera) {
    this.game = game;
    this.camera = camera;
    Gdx.input.setInputProcessor(this);
    nextBlob();
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

  public void update(float delta) {
    if (state == STATE_LIVING) {
      time += delta;
      penaltyTime -= delta;
      if (Settings.getDifficulty() == Difficulty.SCHULE_1_2) {
        timeForShuffle += delta;
        if (timeForShuffle >= 1.25) {
          rotateBuckets();
          timeForShuffle -= 1.25;
        }
      }
      if (Settings.getDifficulty() == Difficulty.SCHULE_3_4) {
        timeForShuffle += delta;
        if (timeForShuffle >= 0.9) {
          shuffleBuckets();
          timeForShuffle -= 0.9;
        }
      }
    }
  }

  private void resetBlob() {
    blobX = 100 + random.nextInt(190);
    blobY = 100 + random.nextInt(430);
  }

  private void nextBlob() {
    resetBlob();
    blobColor = random.nextInt(4);
    if (Settings.getDifficulty() == Difficulty.VORSCHULE) {
      shuffleBuckets();
    }
  }

  private void shuffleBuckets() {
    colorShuffleList.add(0);
    colorShuffleList.add(1);
    colorShuffleList.add(2);
    colorShuffleList.add(3);
    colorAreas[0] = colorShuffleList.remove(random.nextInt(4));
    colorAreas[1] = colorShuffleList.remove(random.nextInt(3));
    colorAreas[2] = colorShuffleList.remove(random.nextInt(2));
    colorAreas[3] = colorShuffleList.remove(0);
  }

  private void rotateBuckets() {
    colorRotateBuffer = colorAreas[0];
    colorAreas[0] = colorAreas[1];
    colorAreas[1] = colorAreas[3];
    colorAreas[3] = colorAreas[2];
    colorAreas[2] = colorRotateBuffer;
  }

  private void handleFinger(int x, int y, int pointer) {
    if (state == STATE_LIVING) {
      if (touchPointerId == -1) {
        if ((x >= blobX) && (x <= (blobX + 90)) && (y >= blobY) && (y <= (blobY + 90))) {
          touchPointerId = pointer;
          touchOffsetX = x - blobX;
          touchOffsetY = y - blobY;
        }
      } else if (touchPointerId == pointer) {
        blobX = x - touchOffsetX;
        blobY = y - touchOffsetY;
        if (blobX < 0) {
          blobX = 0;
        } else if (blobX > 390) {
          blobX = 390;
        }
        if (blobY < 0) {
          blobY = 0;
        } else if (blobY > 630) {
          blobY = 630;
        }
      }
    }
  }

  private void checkSuccess() {
    if ((blobX <= 100) && (blobY >= 529)) {
      touchInArea = 0;
    } else if ((blobX >= 289) && (blobY >= 529)) {
      touchInArea = 1;
    } else if ((blobX <= 100) && (blobY <= 100)) {
      touchInArea = 2;
    } else if ((blobX >= 289) && (blobY <= 100)) {
      touchInArea = 3;
    }
    if (touchInArea > -1) {
      if (colorAreas[blobColor] == touchInArea) {
        successCounter--;
        if (Settings.isSoundOn()) {
          Assets.blub.play(1);
        }
        if (successCounter == 0) {
          finishGame();
        } else {
          nextBlob();
        }
      } else {
        if (Settings.isSoundOn()) {
          Assets.wrong.play(1);
        }
        resetBlob();
        penaltyTime = 2f;
        penaltySum += 2f;
      }
      touchInArea = -1;
    }
  }

  private void finishGame() {
    state = STATE_GAME_OVER;

    int time = (int) ((this.time + this.penaltySum) * 10f);
    finishedPosition = game.highscore.insertScore(time);
  }

  public boolean touchDown(int x, int y, int pointer, int button) {
    camera.unproject(touchPoint.set(x, y, 0));
    handleFinger((int) touchPoint.x, (int) touchPoint.y, pointer);
    return true;
  }

  public boolean touchDragged(int x, int y, int pointer) {
    camera.unproject(touchPoint.set(x, y, 0));
    handleFinger((int) touchPoint.x, (int) touchPoint.y, pointer);
    return true;
  }

  public boolean touchUp(int x, int y, int pointer, int button) {
    if (state == STATE_GAME_OVER) {
      game.setScreen(new StartScreen(game));
    } else if (pointer == touchPointerId) {
      checkSuccess();
      touchPointerId = -1;
    }
    return true;
  }

  public boolean keyDown(int keycode) {
    return false;
  }

  public boolean keyTyped(char character) {
    return false;
  }

  public boolean keyUp(int keycode) {
    return false;
  }

  public boolean scrolled(int amount) {
    return false;
  }

  public boolean touchMoved(int x, int y) {
    return false;
  }
}
