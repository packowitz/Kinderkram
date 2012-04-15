package de.pacworx.farbeimer;

import java.util.List;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import de.pacworx.Settings;
import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Screen;
import de.pacworx.game.Input.TouchEvent;
import de.pacworx.game.impl.AndroidGame;
import de.pacworx.menu.WelcomeScreen;


public class VeryFirstScreen extends Screen {
  private boolean hasHighscore = false;
  private Paint paintTitle;
  private Paint paintSubtitle;
  private Paint paintLos;

  private float time;
  private int color;
  private int xOffset;
  private int yOffset;

  public VeryFirstScreen(Game game) {
    super(game);

    paintTitle = new Paint();
    paintTitle.setColor(Color.BLACK);
    paintTitle.setTextSize(60);
    paintTitle.setTextAlign(Paint.Align.CENTER);

    paintSubtitle = new Paint();
    paintSubtitle.setColor(Color.BLACK);
    paintSubtitle.setTextSize(40);
    paintSubtitle.setTextAlign(Paint.Align.CENTER);

    paintLos = new Paint();
    paintLos.setARGB(255, 255, 255, 224);
    paintLos.setTextSize(34);
    paintLos.setTextAlign(Paint.Align.CENTER);
    paintLos.setTypeface(Typeface.DEFAULT_BOLD);

    time = 0f;
    color = 0;
    xOffset = 0;
    yOffset = 0;
  }

  private void nextColor() {
    if (color < 3) {
      color++;
    } else {
      color = 0;
    }
  }

  @Override
  public void update(float deltaTime) {
    if (hasHighscore) {
      game.setScreen(new HighscoreScreen(game));
    }

    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (TouchEvent event : touchEvents) {
      if ((event.type == TouchEvent.TOUCH_UP)) {
        if ((event.x >= 260) && (event.y >= 600)) {
          game.setScreen(new GameScreen(game));
        } else if ((event.x >= 40) && (event.x <= 150) && (event.y >= 600)) {
          Activity activity = (AndroidGame) game;
          activity.finish();
        } else if (event.y <= 120) {
          game.setScreen(new WelcomeScreen(game, this));
        }
      }
    }

    time += deltaTime;
    if ((time > 1) && (time <= 4)) {
      xOffset = (int) (100f * (time - 1f));
      yOffset = (int) (70f * (time - 1f));
    } else if (time >= 5.2) {
      time -= 5.2;
      nextColor();
      xOffset = 0;
      yOffset = 0;
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();

    g.drawPixmap(Assets.background, 0, 0, 480, 720);

    g.drawText("Farbeimer", 240, 70, paintTitle);
    g.drawText("Stufe: " + Settings.getDifficulty().description, 240, 120, paintSubtitle);

    if (color == 0) {
      g.drawPixmap(Assets.bucket_blue, 350, 190, 90, 90);
      if (time <= 4.4) {
        g.drawPixmap(Assets.blob_blue, 30 + xOffset, 400 - yOffset, 90, 90);
      }
    } else if (color == 1) {
      g.drawPixmap(Assets.bucket_green, 350, 190, 90, 90);
      if (time <= 4.4) {
        g.drawPixmap(Assets.blob_green, 30 + xOffset, 400 - yOffset, 90, 90);
      }
    } else if (color == 2) {
      g.drawPixmap(Assets.bucket_red, 350, 190, 90, 90);
      if (time <= 4.4) {
        g.drawPixmap(Assets.blob_red, 30 + xOffset, 400 - yOffset, 90, 90);
      }
    } else {
      g.drawPixmap(Assets.bucket_yellow, 350, 190, 90, 90);
      if (time <= 4.4) {
        g.drawPixmap(Assets.blob_yellow, 30 + xOffset, 400 - yOffset, 90, 90);
      }
    }
    g.drawPixmap(Assets.hand, 45 + xOffset, 435 - yOffset, 90, 90);

    g.drawPixmap(Assets.back, 40, 580, 110, 110);
    g.drawPixmap(Assets.blob_los, 240, 580, 220, 110);
    g.drawText("LOS", 345, 650, paintLos);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
    hasHighscore = Settings.getFarbeimerSettings().getHighscore() != null;
  }

  @Override
  public void dispose() {
  }

}
