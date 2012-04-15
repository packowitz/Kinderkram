package de.pacworx.farbeimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import de.pacworx.Difficulty;
import de.pacworx.Settings;
import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Pixmap;
import de.pacworx.game.Screen;
import de.pacworx.game.Input.TouchEvent;


public class GameScreen extends Screen {
  private Difficulty difficulty;
  private float time;
  private float penalty = 0;
  private int penaltySum = 0;
  private StringBuffer timeText;
  private float timeForShuffle;
  private Paint paint;
  private Paint paintFinish;
  private Random random = new Random();
  private int finishedPosition = 0;

  private int[] colorAreas = { 0, 1, 2, 3 };
  private List<Integer> colorShuffleList = new ArrayList<Integer>(4);
  private int colorRotateBuffer = 0;

  private int blobColor;
  private int blobX;
  private int blobY;
  private int successCounter = 20;

  private int touchPointerId = -1;
  private int touchOffsetX = 0;
  private int touchOffsetY = 0;
  private int touchInArea = -1;

  public GameScreen(Game game) {
    super(game);
    difficulty = Settings.getDifficulty();
    time = 0f;
    timeText = new StringBuffer();
    timeForShuffle = 0f;

    paint = new Paint();
    paint.setARGB(255, 160, 109, 36);
    paint.setTextSize(28);
    paint.setTextAlign(Paint.Align.CENTER);
    paint.setTypeface(Typeface.DEFAULT_BOLD);

    paintFinish = new Paint();
    paintFinish.setARGB(255, 160, 109, 36);
    paintFinish.setTextSize(50);
    paintFinish.setTextAlign(Paint.Align.CENTER);
    paintFinish.setTypeface(Typeface.DEFAULT_BOLD);

    nextBlob();
  }

  private void nextBlob() {
    blobColor = random.nextInt(4);
    blobX = 100 + random.nextInt(190);
    blobY = 100 + random.nextInt(430);
    if (difficulty == Difficulty.VORSCHULE) {
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

  private void checkSuccess() {
    if ((blobX <= 100) && (blobY <= 100)) {
      touchInArea = 0;
    } else if ((blobX >= 289) && (blobY <= 100)) {
      touchInArea = 1;
    } else if ((blobX <= 100) && (blobY >= 529)) {
      touchInArea = 2;
    } else if ((blobX >= 289) && (blobY >= 529)) {
      touchInArea = 3;
    }
    if (touchInArea > -1) {
      if (colorAreas[blobColor] == touchInArea) {
        successCounter--;
        Assets.blub.play(1);
        if (successCounter == 0) {
          finishGame();
        } else {
          nextBlob();
        }
      } else {
        Assets.wrong.play(1);
        penalty = 2f;
        penaltySum += 2f;
      }
      touchInArea = -1;
    }
  }

  private void finishGame() {
    finishedPosition = 5;

    int time = (int) ((this.time + this.penaltySum) * 10f);
    String date = DateFormat.format("dd.MM  kk:mm", new java.util.Date()).toString();
    Highscore highscore = Settings.getFarbeimerSettings().getHighscore(difficulty);
    if (highscore == null) {
      highscore = new Highscore();
      highscore.difficulty = difficulty;
    }
    if ((highscore.fourthTime == 0) || (time < highscore.fourthTime)) {
      if ((highscore.thirdTime == 0) || (time < highscore.thirdTime)) {
        highscore.fourthTime = highscore.thirdTime;
        highscore.fourthDate = highscore.thirdDate;
        if ((highscore.secondTime == 0) || (time < highscore.secondTime)) {
          highscore.thirdTime = highscore.secondTime;
          highscore.thirdDate = highscore.secondDate;
          if ((highscore.firstTime == 0) || (time < highscore.firstTime)) {
            highscore.secondTime = highscore.firstTime;
            highscore.secondDate = highscore.firstDate;
            highscore.firstTime = time;
            highscore.firstDate = date;
            finishedPosition = 1;
          } else {
            highscore.secondTime = time;
            highscore.secondDate = date;
            finishedPosition = 2;
          }
        } else {
          highscore.thirdTime = time;
          highscore.thirdDate = date;
          finishedPosition = 3;
        }
      } else {
        highscore.fourthTime = time;
        highscore.fourthDate = date;
        finishedPosition = 4;
      }
      Settings.getFarbeimerSettings().saveHighscore(highscore);
    }
  }

  @Override
  public void update(float deltaTime) {
    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (TouchEvent event : touchEvents) {
      if ((finishedPosition > 0) && (event.type == TouchEvent.TOUCH_UP)) {
        game.setScreen(new HighscoreScreen(game));
      } else {
        if ((event.type == TouchEvent.TOUCH_DOWN) && (touchPointerId == -1)) {
          if ((event.x >= blobX) && (event.x <= (blobX + 100)) && (event.y >= blobY) && (event.y <= (blobY + 100))) {
            touchPointerId = event.pointer;
            touchOffsetX = event.x - blobX;
            touchOffsetY = event.y - blobY;
          }
        }
        if ((event.type == TouchEvent.TOUCH_DRAGGED) && (event.pointer == touchPointerId)) {
          blobX = event.x - touchOffsetX;
          blobY = event.y - touchOffsetY;
          if (blobX < 0) {
            blobX = 0;
          } else if (blobX > 389) {
            blobX = 389;
          }
          if (blobY < 0) {
            blobY = 0;
          } else if (blobY > 629) {
            blobY = 629;
          }
        }
        if ((event.type == TouchEvent.TOUCH_UP) && (event.pointer == touchPointerId)) {
          checkSuccess();
          touchPointerId = -1;
        }
      }
    }
    if (finishedPosition == 0) {
      time += deltaTime;
      penalty -= deltaTime;
      if (difficulty == Difficulty.SCHULE_1_2) {
        timeForShuffle += deltaTime;
        if (timeForShuffle >= 1.25) {
          rotateBuckets();
          timeForShuffle -= 1.25;
        }
      }
      if (difficulty == Difficulty.SCHULE_3_4) {
        timeForShuffle += deltaTime;
        if (timeForShuffle >= 0.9) {
          shuffleBuckets();
          timeForShuffle -= 0.9;
        }
      }
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();

    g.drawPixmap(Assets.background, 0, 0, 480, 720);

    drawBucket(g, Assets.bucket_blue, colorAreas[0]);
    drawBucket(g, Assets.bucket_green, colorAreas[1]);
    drawBucket(g, Assets.bucket_red, colorAreas[2]);
    drawBucket(g, Assets.bucket_yellow, colorAreas[3]);

    timeText.setLength(0);
    timeText.append((int) (time / 60)).append(':');
    if ((time % 60) < 10) {
      timeText.append('0');
    }
    timeText.append((int) (time % 60));
    if (penaltySum > 0) {
      timeText.append(" + ");
      timeText.append(penaltySum);
    }
    if (penalty >= 0) {
      paint.setARGB(255, 206, 10, 13);
      g.drawText(timeText.toString(), 240, 38, paint);
      paint.setARGB(255, 160, 109, 36);
    } else {
      g.drawText(timeText.toString(), 240, 38, paint);
    }
    g.drawText(Integer.toString(successCounter), 240, 704, paint);

    if (finishedPosition == 0) {
      if (blobColor == 0) {
        g.drawPixmap(Assets.blob_blue, blobX, blobY, 90, 90);
      } else if (blobColor == 1) {
        g.drawPixmap(Assets.blob_green, blobX, blobY, 90, 90);
      } else if (blobColor == 2) {
        g.drawPixmap(Assets.blob_red, blobX, blobY, 90, 90);
      } else if (blobColor == 3) {
        g.drawPixmap(Assets.blob_yellow, blobX, blobY, 90, 90);
      }
    } else {
      timeText.setLength(0);
      timeText.append((int) ((time + penaltySum) / 60)).append(':');
      if (((time + penaltySum) % 60) < 10) {
        timeText.append('0');
      }
      timeText.append((int) ((time + penaltySum) % 60));
      timeText.append('.');
      timeText.append((int) ((time * 10f) % 10));
      paintFinish.setARGB(255, 160, 109, 36);
      g.drawText(timeText.toString(), 240, 395, paintFinish);
      paintFinish.setARGB(255, 255, 160, 66);
      if (finishedPosition == 1) {
        g.drawPixmap(Assets.blob_yellow, 160, 160, 160, 160);
        g.drawText("1", 243, 268, paintFinish);
      } else if (finishedPosition == 2) {
        g.drawPixmap(Assets.blob_green, 160, 160, 160, 160);
        g.drawText("2", 240, 265, paintFinish);
      } else if (finishedPosition == 3) {
        g.drawPixmap(Assets.blob_blue, 160, 160, 160, 160);
        g.drawText("3", 248, 249, paintFinish);
      } else if (finishedPosition == 4) {
        g.drawPixmap(Assets.blob_red, 160, 160, 160, 160);
        g.drawText("4", 244, 254, paintFinish);
      }
    }
  }

  private void drawBucket(Graphics g, Pixmap pixmap, int position) {
    switch (position) {
      case 0: {
        g.drawPixmap(pixmap, 10, 10, 90, 90);
        break;
      }

      case 1: {
        g.drawPixmap(pixmap, 379, 10, 90, 90);
        break;
      }

      case 2: {
        g.drawPixmap(pixmap, 10, 619, 90, 90);
        break;
      }

      case 3: {
        g.drawPixmap(pixmap, 379, 619, 90, 90);
      }
    }
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
  }

}
