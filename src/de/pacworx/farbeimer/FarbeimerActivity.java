package de.pacworx.farbeimer;

import de.pacworx.Settings;
import de.pacworx.game.Screen;
import de.pacworx.game.impl.AndroidGame;


public class FarbeimerActivity extends AndroidGame {
  @Override
  public Screen getStartScreen() {
    Assets.init(this);

    Highscore highscore = Settings.getFarbeimerSettings().getHighscore(Settings.getDifficulty());
    if (highscore == null) {
      return new VeryFirstScreen(this);
    }
    return new HighscoreScreen(this);
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
