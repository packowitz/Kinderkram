package de.pacworx.menu;

import de.pacworx.game.Game;
import de.pacworx.game.Graphics;
import de.pacworx.game.Pixmap;
import de.pacworx.game.Graphics.PixmapFormat;


public class Assets {
  public static Pixmap logo;
  public static Pixmap chooseAge;
  public static Pixmap difficulty0;
  public static Pixmap difficulty1;
  public static Pixmap difficulty2;
  public static Pixmap difficulty3;
  public static Pixmap settings;

  public static Pixmap farbeimerIcon;
  public static Pixmap fallobstIcon;
  public static Pixmap playgroundIcon;

  public static void init(Game game) {
    Graphics graphics = game.getGraphics();
    logo = graphics.newPixmap("logo.png", PixmapFormat.RGB565);
    chooseAge = graphics.newPixmap("chooseAge.png", PixmapFormat.RGB565);
    difficulty0 = graphics.newPixmap("difficulty0.png", PixmapFormat.RGB565);
    difficulty1 = graphics.newPixmap("difficulty1.png", PixmapFormat.RGB565);
    difficulty2 = graphics.newPixmap("difficulty2.png", PixmapFormat.RGB565);
    difficulty3 = graphics.newPixmap("difficulty3.png", PixmapFormat.RGB565);
    settings = graphics.newPixmap("settings.png", PixmapFormat.RGB565);
    farbeimerIcon = graphics.newPixmap("farbeimer/icon.png", PixmapFormat.RGB565);
    fallobstIcon = graphics.newPixmap("fallobst/red.png", PixmapFormat.RGB565);

    playgroundIcon = graphics.newPixmap("test/playgroundIcon.png", PixmapFormat.RGB565);
  }
}
