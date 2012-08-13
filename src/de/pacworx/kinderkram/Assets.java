package de.pacworx.kinderkram;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {
  public static Texture atlas;
  public static TextureRegion settings;

  public static void init() {
    atlas = new Texture(Gdx.files.internal("atlas.png"));
    settings = new TextureRegion(atlas, 0, 0, 320, 480);
  }

  public static void dispose() {
    atlas.dispose();
  }
}
