package de.pacworx.fallobst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {
  public static Texture atlas;
  public static TextureRegion background;
  public static TextureRegion apple;
  public static TextureRegion basket;

  public static void init() {
    atlas = new Texture(Gdx.files.internal("fallobst/atlas.png"));
    background = new TextureRegion(atlas, 0, 0, 320, 480);
    apple = new TextureRegion(atlas, 480, 0, 32, 32);
    basket = new TextureRegion(atlas, 320, 0, 128, 128);
  }

  public static void dispose() {
    atlas.dispose();
  }
}
