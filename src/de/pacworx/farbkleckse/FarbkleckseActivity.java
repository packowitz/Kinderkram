package de.pacworx.farbkleckse;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.pacworx.Difficulty;
import de.pacworx.R;
import de.pacworx.Settings;


public class FarbkleckseActivity extends AndroidApplication {
  private FarbkleckseGame game;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
    cfg.useWakelock = true;

    game = new FarbkleckseGame(this);
    initialize(game, cfg);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(de.pacworx.R.menu.game_menu, menu);
    return true;
  }

  @Override
  public boolean onMenuOpened(int featureId, Menu menu) {
    for (int i = 0; i < menu.size(); i++) {
      MenuItem item = menu.getItem(i);
      if (item.getItemId() == R.id.sound_on) {
        item.setVisible(!Settings.isSoundOn());
      } else if (item.getItemId() == R.id.sound_off) {
        item.setVisible(Settings.isSoundOn());
      }
    }
    game.getScreen().pause();
    return super.onMenuOpened(featureId, menu);
  }

  @Override
  public void onOptionsMenuClosed(Menu menu) {
    game.getScreen().resume();
    super.onOptionsMenuClosed(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    game.getScreen().resume();
    switch (item.getItemId()) {
      case R.id.sound_on: {
        Settings.enableSound();
        break;
      }

      case R.id.sound_off: {
        Settings.disableSound();
        break;
      }

      case R.id.back: {
        onBackPressed();
        break;
      }

      case R.id.play: {
        if (game.getScreen() instanceof StartScreen) {
          game.setScreen(new GameScreen(game));
        }
        break;
      }

      case R.id.dif_kindergarten: {
        Settings.setDifficulty(Difficulty.KINDERGARTEN);
        if (game.getScreen() instanceof GameScreen) {
          game.setScreen(new GameScreen(game));
        }
        break;
      }

      case R.id.dif_vorschule: {
        Settings.setDifficulty(Difficulty.VORSCHULE);
        if (game.getScreen() instanceof GameScreen) {
          game.setScreen(new GameScreen(game));
        }
        break;
      }

      case R.id.dif_1_2_klasse: {
        Settings.setDifficulty(Difficulty.SCHULE_1_2);
        if (game.getScreen() instanceof GameScreen) {
          game.setScreen(new GameScreen(game));
        }
        break;
      }

      case R.id.dif_3_4_klasse: {
        Settings.setDifficulty(Difficulty.SCHULE_3_4);
        if (game.getScreen() instanceof GameScreen) {
          game.setScreen(new GameScreen(game));
        }
        break;
      }
    }
    return true;
  }

  @Override
  public void onBackPressed() {
    Screen screen = game.getScreen();
    if (screen instanceof StartScreen) {
      super.onBackPressed();
    } else {
      game.setScreen(new StartScreen(game));
    }
  }
}
