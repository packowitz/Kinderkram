package de.pacworx.menu;

import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Screen;
import de.pacworx.game.Input.TouchEvent;
import de.pacworx.game.impl.AndroidGame;


public class MenuScreen extends Screen {
  Paint paint;

  public MenuScreen(Game game) {
    super(game);
    paint = new Paint();
    paint.setColor(Color.BLACK);
    paint.setTextSize(35);

    Typeface typeface = Typeface.createFromAsset(((AndroidGame) game).getAssets(), "edo.ttf");
    paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.LEFT);
  }

  @Override
  public void update(float deltaTime) {
    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (TouchEvent event : touchEvents) {
      if (event.type == TouchEvent.TOUCH_UP) {
        if (event.y >= 619) {
          game.setScreen(new WelcomeScreen(game));
        } else if ((event.y >= 60) && (event.y < 150)) {
          try {
            Class clazz = Class.forName("de.pacworx.farbkleckse.FarbkleckseActivity");
            Activity activity = (AndroidGame) game;
            Intent intent = new Intent(activity, clazz);
            activity.startActivity(intent);
          } catch (ClassNotFoundException e) {
            Log.e("Kinderleicht", e.getMessage(), e);
          }
        } else if ((event.y >= 160) && (event.y < 250)) {
          try {
            Class clazz = Class.forName("de.pacworx.fallobst.FallobstActivity");
            Activity activity = (AndroidGame) game;
            Intent intent = new Intent(activity, clazz);
            activity.startActivity(intent);
          } catch (ClassNotFoundException e) {
            Log.e("Kinderleicht", e.getMessage(), e);
          }
        } else if ((event.y >= 260) && (event.y < 350)) {
          try {
            Class clazz = Class.forName("de.pacworx.test.Playground");
            Activity activity = (AndroidGame) game;
            Intent intent = new Intent(activity, clazz);
            activity.startActivity(intent);
          } catch (ClassNotFoundException e) {
            Log.e("Kinderleicht", e.getMessage(), e);
          }
        }
      }
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();
    g.clear(255, 255, 255);
    paint.setTextAlign(Paint.Align.CENTER);
    g.drawText("Spiele", 240, 45, paint);

    paint.setTextAlign(Paint.Align.LEFT);
    g.drawPixmap(Assets.farbkleckseIcon, 10, 60, 90, 90);
    g.drawText("Is ja'n Klecks", 110, 115, paint);

    g.drawPixmap(Assets.fallobstIcon, 10, 160, 90, 90);
    g.drawText("Apfelernte", 110, 215, paint);

    g.drawPixmap(Assets.playgroundIcon, 10, 260, 90, 90);
    g.drawText("Playground", 110, 315, paint);

    g.drawPixmap(Assets.settings, 10, 619, 90, 90);
    g.drawText("Einstellungen", 110, 680, paint);
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
