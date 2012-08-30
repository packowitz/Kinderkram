package de.pacworx.farbkleckse;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {
  public static Texture atlas;
  public static TextureRegion background;
  public static TextureRegion bucket_blue;
  public static TextureRegion bucket_yellow;
  public static TextureRegion bucket_green;
  public static TextureRegion bucket_red;
  public static TextureRegion blob_blue;
  public static TextureRegion blob_yellow;
  public static TextureRegion blob_green;
  public static TextureRegion blob_red;
  public static TextureRegion hs_1;
  public static TextureRegion hs_2;
  public static TextureRegion hs_3;
  public static TextureRegion hs_4;
  public static TextureRegion hand;
  public static TextureRegion back;
  public static TextureRegion settings;
  public static TextureRegion blob_los;

  public static Sound blub;
  public static Sound wrong;

  public static void init() {
    atlas = new Texture(Gdx.files.internal("farbkleckse/atlas.png"));
    background = new TextureRegion(atlas, 0, 0, 240, 360);
    bucket_blue = new TextureRegion(atlas, 240, 0, 64, 64);
    bucket_yellow = new TextureRegion(atlas, 240, 64, 64, 64);
    bucket_green = new TextureRegion(atlas, 240, 128, 64, 64);
    bucket_red = new TextureRegion(atlas, 240, 192, 64, 64);
    blob_blue = new TextureRegion(atlas, 304, 0, 64, 64);
    blob_yellow = new TextureRegion(atlas, 304, 64, 64, 64);
    blob_green = new TextureRegion(atlas, 304, 128, 64, 64);
    blob_red = new TextureRegion(atlas, 304, 192, 64, 64);
    hs_1 = new TextureRegion(atlas, 368, 0, 64, 64);
    hs_2 = new TextureRegion(atlas, 368, 64, 64, 64);
    hs_3 = new TextureRegion(atlas, 368, 128, 64, 64);
    hs_4 = new TextureRegion(atlas, 368, 192, 64, 64);
    hand = new TextureRegion(atlas, 432, 0, 64, 64);
    back = new TextureRegion(atlas, 432, 64, 64, 64);
    settings = new TextureRegion(atlas, 432, 128, 64, 64);
    blob_los = new TextureRegion(atlas, 240, 256, 128, 64);

    blub = Gdx.audio.newSound(Gdx.files.internal("farbkleckse/blub.ogg"));
    wrong = Gdx.audio.newSound(Gdx.files.internal("farbkleckse/wrong.ogg"));
  }

  public static TextureRegion getBucket(int color) {
    if (color == 0) {
      return bucket_blue;
    }
    if (color == 1) {
      return bucket_green;
    }
    if (color == 2) {
      return bucket_red;
    }
    if (color == 3) {
      return bucket_yellow;
    }
    Log.e("Kinderkram", "Farbkleckse: invalid color: " + color);
    return null;
  }

  public static TextureRegion getBlob(int color) {
    if (color == 0) {
      return blob_blue;
    }
    if (color == 1) {
      return blob_green;
    }
    if (color == 2) {
      return blob_red;
    }
    if (color == 3) {
      return blob_yellow;
    }
    Log.e("Kinderkram", "Farbkleckse: invalid color: " + color);
    return null;
  }

  public static TextureRegion getHighscore(int position) {
    if (position == 1) {
      return hs_1;
    }
    if (position == 2) {
      return hs_2;
    }
    if (position == 3) {
      return hs_3;
    }
    if (position == 4) {
      return hs_4;
    }
    Log.e("Kinderkram", "Farbkleckse: invalid position: " + position);
    return null;
  }

  public static void dispose() {
    atlas.dispose();
  }
}
