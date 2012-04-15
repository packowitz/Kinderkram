package de.pacworx.menu;

import java.util.List;
import de.pacworx.Difficulty;
import de.pacworx.Settings;
import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Screen;
import de.pacworx.game.Input.TouchEvent;


public class WelcomeScreen extends Screen {
  private Screen returnScreen;

  public WelcomeScreen(Game game) {
    super(game);
  }

  public WelcomeScreen(Game game, Screen returnScreen) {
    this(game);
    this.returnScreen = returnScreen;
  }

  @Override
  public void update(float deltaTime) {
    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
    game.getInput().getKeyEvents();

    for (TouchEvent event : touchEvents) {
      if ((event.type == TouchEvent.TOUCH_UP) && (event.y >= 432)) {
        if (event.y < 504) {
          Settings.setDifficulty(Difficulty.KINDERGARTEN);
        } else if (event.y < 576) {
          Settings.setDifficulty(Difficulty.VORSCHULE);
        } else if (event.y < 648) {
          Settings.setDifficulty(Difficulty.SCHULE_1_2);
        } else {
          Settings.setDifficulty(Difficulty.SCHULE_3_4);
        }
        if (returnScreen != null) {
          game.setScreen(returnScreen);
        } else {
          game.setScreen(new MenuScreen(game));
        }
      }
    }
  }

  @Override
  public void present(float deltaTime) {
    Graphics g = game.getGraphics();
    g.drawPixmap(Assets.logo, 0, 0);
    g.drawPixmap(Assets.chooseAge, 0, 360);
    g.drawPixmap(Assets.difficulty0, 0, 432);
    g.drawPixmap(Assets.difficulty1, 0, 504);
    g.drawPixmap(Assets.difficulty2, 0, 576);
    g.drawPixmap(Assets.difficulty3, 0, 648);
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
