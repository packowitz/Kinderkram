package de.pacworx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import de.pacworx.farbeimer.FarbeimerSettings;
import android.os.Environment;
import android.util.Log;


public class Settings {
  private static String externalStoragePath;
  private static Properties properties = new Properties();
  private static Difficulty difficulty;

  public static void loadSettings() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(
          externalStoragePath + "/kinderkram/settings"));
        properties.load(stream);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    }
  }

  public static void save() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        FileOutputStream stream = new FileOutputStream(externalStoragePath + "/kinderkram/settings");
        properties.store(stream, null);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    } else {
      Log.w("Kinderkram", "Cannot write to disk because media is not mounted");
    }
  }

  public static FarbeimerSettings getFarbeimerSettings() {
    return new FarbeimerSettings(properties);
  }

  public static Difficulty getDifficulty() {
    if (difficulty == null) {
      String difficultyId = properties.getProperty("difficulty");
      if (difficultyId != null) {
        difficulty = Difficulty.getById(difficultyId);
      }
    }
    return difficulty;
  }

  public static void setDifficulty(Difficulty newDifficulty) {
    properties.put("difficulty", newDifficulty.id);
    difficulty = newDifficulty;
    save();
  }
}
