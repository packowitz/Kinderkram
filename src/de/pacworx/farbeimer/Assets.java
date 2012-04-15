package de.pacworx.farbeimer;

import de.pacworx.game.Audio;
import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Pixmap;
import de.pacworx.game.Sound;
import de.pacworx.game.Graphics.PixmapFormat;


public class Assets {
  private static boolean init = false;

  public static Pixmap background;
  public static Pixmap bucket_red;
  public static Pixmap bucket_green;
  public static Pixmap bucket_yellow;
  public static Pixmap bucket_blue;
  public static Pixmap blob_red;
  public static Pixmap blob_green;
  public static Pixmap blob_yellow;
  public static Pixmap blob_blue;
  public static Pixmap hand;
  public static Pixmap back;
  public static Pixmap blob_los;

  public static Sound blub;
  public static Sound wrong;

  public static void init(Game game) {
    if (!init) {
      init = true;

      Graphics graphics = game.getGraphics();
      Audio audio = game.getAudio();
      background = graphics.newPixmap("farbeimer/background.png", PixmapFormat.RGB565);
      bucket_red = graphics.newPixmap("farbeimer/eimer_rot_64x64.png", PixmapFormat.RGB565);
      bucket_green = graphics.newPixmap("farbeimer/eimer_gruen_64x64.png", PixmapFormat.RGB565);
      bucket_yellow = graphics.newPixmap("farbeimer/eimer_gelb_64x64.png", PixmapFormat.RGB565);
      bucket_blue = graphics.newPixmap("farbeimer/eimer_blau_64x64.png", PixmapFormat.RGB565);
      blob_red = graphics.newPixmap("farbeimer/fleck_rot_64x64.png", PixmapFormat.RGB565);
      blob_green = graphics.newPixmap("farbeimer/fleck_gruen_64x64.png", PixmapFormat.RGB565);
      blob_yellow = graphics.newPixmap("farbeimer/fleck_gelb_64x64.png", PixmapFormat.RGB565);
      blob_blue = graphics.newPixmap("farbeimer/fleck_blau_64x64.png", PixmapFormat.RGB565);
      hand = graphics.newPixmap("farbeimer/hand.png", PixmapFormat.RGB565);
      back = graphics.newPixmap("farbeimer/back.png", PixmapFormat.RGB565);
      blob_los = graphics.newPixmap("farbeimer/fleck_los.png", PixmapFormat.RGB565);

      blub = audio.newSound("farbeimer/blub.ogg");
      wrong = audio.newSound("farbeimer/wrong.ogg");
    }
  }
}
