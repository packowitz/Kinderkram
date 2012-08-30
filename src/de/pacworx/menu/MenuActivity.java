package de.pacworx.menu;

import de.pacworx.Difficulty;
import de.pacworx.Settings;
import de.pacworx.game.Screen;
import de.pacworx.game.impl.AndroidGame;


public class MenuActivity extends AndroidGame {
  public Screen getStartScreen() {
    Settings.loadSettings();
    Assets.init(this);

    Difficulty difficulty = Settings.getDifficulty();
    if (difficulty == null) {
      return new WelcomeScreen(this);
    }
    return new MenuScreen(this);
  }

  @Override
  public int getPortraitWidth() {
    return 480;
  }

  @Override
  public int getPortraitHeight() {
    return 720;
  }
}
