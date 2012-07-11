package de.pacworx.kinderkram;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import android.os.Environment;
import android.util.Log;
import de.pacworx.Difficulty;


public class Highscore {
  public Difficulty difficulty;
  public List<HighscoreEntry> scores = new ArrayList<HighscoreEntry>();
  private boolean ascendantOrder;
  private int maxSize;
  private String gameName;
  private Properties properties;

  public Highscore(Difficulty difficulty, boolean ascendantOrder, int maxSize, String gameName) {
    this.difficulty = difficulty;
    this.ascendantOrder = ascendantOrder;
    this.maxSize = maxSize;
    this.gameName = gameName;
    this.properties = new Properties();
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(
          externalStoragePath + "/kinderkram/" + gameName));
        this.properties.load(stream);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    } else {
      Log.w("Kinderkram", "External storage not available");
    }
    readScores();
  }

  private void readScores() {
    String date, scoreString;
    for (int i = 0; i < maxSize; i++) {
      scoreString = properties.getProperty(difficulty.name() + "." + i + ".score");
      if (scoreString == null) {
        break;
      }
      date = properties.getProperty(difficulty.name() + "." + i + ".date");
      scores.add(new HighscoreEntry(Integer.parseInt(scoreString), date));
    }
  }

  private void writeScores() {
    for (int i = 0; i < scores.size(); i++) {
      HighscoreEntry entry = scores.get(i);
      properties.put(difficulty.name() + "." + i + ".score", entry.score);
      properties.put(difficulty.name() + "." + i + ".date", entry.date);
    }

    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        String externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        FileOutputStream stream = new FileOutputStream(externalStoragePath + "/kinderkram/" + gameName);
        properties.store(stream, null);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    } else {
      Log.w("Kinderkram", "Cannot write to disk because media is not mounted");
    }
  }

  public int insertScore(int score) {
    int position = 0;
    for (int i = 0; i < scores.size(); i++) {
      HighscoreEntry entry = scores.get(i);
      if ((ascendantOrder && (score > entry.score)) || (!ascendantOrder && (score < entry.score))) {
        position = i + 1;

        HighscoreEntry newEntry = new HighscoreEntry(score);
        scores.add(i, newEntry);
        while (scores.size() > maxSize) {
          scores.remove(scores.size() - 1);
        }
        break;
      }
    }
    if ((position == 0) && (scores.size() < maxSize)) {
      HighscoreEntry newEntry = new HighscoreEntry(score);
      scores.add(newEntry);
      position = scores.size();
    }
    if (position > 0) {
      writeScores();
    }

    return position;
  }

}
