package de.pacworx.farbeimer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.pacworx.Difficulty;
import de.pacworx.Settings;


public class FarbeimerSettings {
  private Properties properties;
  private Map<Difficulty, Highscore> highscoreMap;

  public FarbeimerSettings(Properties properties) {
    this.properties = properties;
    highscoreMap = new HashMap<Difficulty, Highscore>();
  }

  public Highscore getHighscore() {
    return getHighscore(Settings.getDifficulty());
  }

  public Highscore getHighscore(Difficulty difficulty) {
    Highscore highscore = highscoreMap.get(difficulty);
    if (highscore == null) {
      String timeString = properties.getProperty("farbeimer." + difficulty.name() + ".first.time");
      if (timeString == null) {
        return null;
      }
      highscore = new Highscore();

      highscore.difficulty = difficulty;
      highscore.firstTime = Integer.parseInt(timeString);
      highscore.firstDate = properties.getProperty("farbeimer." + difficulty.name() + ".first.date");
      timeString = properties.getProperty("farbeimer." + difficulty.name() + ".second.time");
      if (timeString != null) {
        highscore.secondTime = Integer.parseInt(timeString);
        highscore.secondDate = properties.getProperty("farbeimer." + difficulty.name() + ".second.date");
        timeString = properties.getProperty("farbeimer." + difficulty.name() + ".third.time");
        if (timeString != null) {
          highscore.thirdTime = Integer.parseInt(timeString);
          highscore.thirdDate = properties.getProperty("farbeimer." + difficulty.name() + ".third.date");
          timeString = properties.getProperty("farbeimer." + difficulty.name() + ".fourth.time");
          if (timeString != null) {
            highscore.fourthTime = Integer.parseInt(timeString);
            highscore.fourthDate = properties.getProperty("farbeimer." + difficulty.name() + ".fourth.date");
          }
        }
      }
      highscoreMap.put(difficulty, highscore);
    }
    return highscore;
  }

  public void saveHighscore(Highscore highscore) {
    highscoreMap.put(highscore.difficulty, highscore);
    if (highscore.firstTime > 0) {
      properties.put("farbeimer." + highscore.difficulty.name() + ".first.time", Integer.toString(highscore.firstTime));
      properties.put("farbeimer." + highscore.difficulty.name() + ".first.date", highscore.firstDate);
    }
    if (highscore.secondTime > 0) {
      properties.put("farbeimer." + highscore.difficulty.name() + ".second.time",
        Integer.toString(highscore.secondTime));
      properties.put("farbeimer." + highscore.difficulty.name() + ".second.date", highscore.secondDate);
    }
    if (highscore.thirdTime > 0) {
      properties.put("farbeimer." + highscore.difficulty.name() + ".third.time", Integer.toString(highscore.thirdTime));
      properties.put("farbeimer." + highscore.difficulty.name() + ".third.date", highscore.thirdDate);
    }
    if (highscore.fourthTime > 0) {
      properties.put("farbeimer." + highscore.difficulty.name() + ".fourth.time",
        Integer.toString(highscore.fourthTime));
      properties.put("farbeimer." + highscore.difficulty.name() + ".fourth.date", highscore.fourthDate);
    }
    Settings.save();
  }
}
