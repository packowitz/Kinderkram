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


public class HighscoreScreen extends Screen {
  private Highscore highscore;
  private Paint paintTitle;
  private Paint paintSubtitle;
  private Paint paintRanking;
  private Paint paintRankingText;
  private Paint paintLos;
  StringBuffer buffer = new StringBuffer();

  public HighscoreScreen(Game game) {
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

    paintRanking = new Paint();
    paintRanking.setARGB(255, 255, 160, 66);
    paintRanking.setTextSize(32);
    paintRanking.setTextAlign(Paint.Align.CENTER);
    paintRanking.setTypeface(Typeface.DEFAULT_BOLD);

    paintRankingText = new Paint();
    paintRankingText.setARGB(255, 160, 109, 36);
    paintRankingText.setTextSize(30);
    paintRankingText.setTextAlign(Paint.Align.LEFT);
    paintRankingText.setTypeface(Typeface.DEFAULT_BOLD);
  }

  @Override
  public void update(float deltaTime) {
    highscore = Settings.getFarbeimerSettings().getHighscore();
    if (highscore == null) {
      game.setScreen(new VeryFirstScreen(game));
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
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();

    g.drawPixmap(Assets.background, 0, 0, 480, 720);

    g.drawText("Farbeimer", 240, 70, paintTitle);
    g.drawText("Stufe: " + Settings.getDifficulty().description, 240, 120, paintSubtitle);

    g.drawPixmap(Assets.blob_yellow, 10, 160, 90, 90);
    g.drawText("1", 55, 221, paintRanking);
    g.drawText(timeToString(highscore.firstTime) + "  " + highscore.firstDate, 110, 221, paintRankingText);

    if (highscore.secondTime > 0) {
      g.drawPixmap(Assets.blob_green, 10, 260, 90, 90);
      g.drawText("2", 56, 319, paintRanking);
      g.drawText(timeToString(highscore.secondTime) + "  " + highscore.secondDate, 110, 319, paintRankingText);
    }

    if (highscore.thirdTime > 0) {
      g.drawPixmap(Assets.blob_blue, 10, 360, 90, 90);
      g.drawText("3", 60, 412, paintRanking);
      g.drawText(timeToString(highscore.thirdTime) + "  " + highscore.thirdDate, 110, 412, paintRankingText);
    }

    if (highscore.fourthTime > 0) {
      g.drawPixmap(Assets.blob_red, 10, 460, 90, 90);
      g.drawText("4", 57, 514, paintRanking);
      g.drawText(timeToString(highscore.fourthTime) + "  " + highscore.fourthDate, 110, 514, paintRankingText);
    }

    g.drawPixmap(Assets.back, 40, 580, 110, 110);
    g.drawPixmap(Assets.blob_los, 240, 580, 220, 110);
    g.drawText("LOS", 345, 650, paintLos);
  }

  private String timeToString(int time) {
    buffer.setLength(0);
    buffer.append(time / 600);
    buffer.append(':');
    if ((time % 600) < 100) {
      buffer.append('0');
    }
    buffer.append((time % 600) / 10);
    buffer.append('.');
    buffer.append(time % 10);
    return buffer.toString();
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
