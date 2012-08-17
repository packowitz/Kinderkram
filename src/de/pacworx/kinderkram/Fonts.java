package de.pacworx.kinderkram;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Fonts {
  private static boolean init = false;
  public static BitmapFont calibri_gold;
  public static BitmapFont calibri_red;
  public static BitmapFont calibri_brown;
  public static BitmapFont calibri_brown_big;
  public static BitmapFont highscore_grey;
  public static BitmapFont title_red;
  public static BitmapFont level_red;

  public static void init() {
    if (!init) {
      calibri_gold = new BitmapFont(Gdx.files.internal("calibri.fnt"), false);
      calibri_red = new BitmapFont(Gdx.files.internal("calibri_red.fnt"), false);
      calibri_brown = new BitmapFont(Gdx.files.internal("calibri_brown.fnt"), false);
      calibri_brown_big = new BitmapFont(Gdx.files.internal("calibri_brown_big.fnt"), false);
      highscore_grey = new BitmapFont(Gdx.files.internal("bubbles.fnt"), false);
      title_red = new BitmapFont(Gdx.files.internal("bubbles_title.fnt"), false);
      level_red = new BitmapFont(Gdx.files.internal("bubbles_level.fnt"), false);
    }
  }
}
