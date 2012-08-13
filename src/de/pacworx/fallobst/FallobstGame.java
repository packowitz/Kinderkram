package de.pacworx.fallobst;

import android.app.Activity;
import com.badlogic.gdx.Game;
import de.pacworx.kinderkram.Highscore;


public class FallobstGame extends Game {
  private Activity activity;
  public Highscore highscore;

  public FallobstGame(Activity activity) {
    this.activity = activity;
    highscore = new Highscore(true, 4, "apfelernte");
  }

  public void finish() {
    activity.finish();
  }

  public Activity getActivity() {
    return activity;
  }

  @Override
  public void create() {
    Assets.init();
    setScreen(new StartScreen(this));
  }

  @Override
  public void dispose() {
    super.dispose();
    Assets.dispose();
  }

}
