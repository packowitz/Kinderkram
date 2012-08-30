package de.pacworx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import android.os.Environment;
import android.util.Log;


public class Settings {
  private static String externalStoragePath;
  private static Properties properties = new Properties();
  private static Difficulty difficulty;
  private static Boolean soundOn;

  public static void loadSettings() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        File dir = new File(externalStoragePath + "kinderkram");
        if (!dir.exists() || !dir.isDirectory()) {
          dir.mkdir();
        }

        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(
          externalStoragePath + "kinderkram/settings"));
        properties.load(stream);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    }
  }

  public static void save() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      try {
        FileOutputStream stream = new FileOutputStream(externalStoragePath + "kinderkram/settings");
        properties.store(stream, null);
      } catch (IOException e) {
        Log.e("Kinderkram", e.getMessage(), e);
      }
    } else {
      Log.w("Kinderkram", "Cannot write to disk because media is not mounted");
    }
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

  public static boolean isSoundOn() {
    if (soundOn == null) {
      String soundOnProperty = properties.getProperty("sound");
      if (soundOnProperty != null) {
        soundOn = Boolean.parseBoolean(soundOnProperty);
      } else {
        soundOn = true;
      }
    }
    return soundOn;
  }

  public static void enableSound() {
    setSoundOn(true);
  }

  public static void disableSound() {
    setSoundOn(false);
  }

  private static void setSoundOn(boolean newSoundOn) {
    properties.put("sound", Boolean.toString(newSoundOn));
    soundOn = newSoundOn;
    save();
  }
}
