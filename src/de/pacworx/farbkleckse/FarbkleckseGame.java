package de.pacworx.farbkleckse;

import android.app.Activity;
import com.badlogic.gdx.Game;
import de.pacworx.kinderkram.Fonts;
import de.pacworx.kinderkram.Highscore;


public class FarbkleckseGame extends Game {
  private Activity activity;
  public Highscore highscore;

  public FarbkleckseGame(Activity activity) {
    this.activity = activity;
    highscore = new Highscore(false, 4, "farbkleckse");
  }

  public void finish() {
    activity.finish();
  }

  public Activity getActivity() {
    return activity;
  }

  public void create() {
    Assets.init();
    Fonts.init();
    setScreen(new StartScreen(this));
  }

  @Override
  public void dispose() {
    super.dispose();
    Assets.dispose();
  }
}
