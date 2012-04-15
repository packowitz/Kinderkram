package de.pacworx.game;

public interface Game {
  Input getInput();

  FileIO getFileIO();

  Graphics getGraphics();

  Audio getAudio();

  void setScreen(Screen screen);

  Screen getCurrentScreen();

  Screen getStartScreen();
}
