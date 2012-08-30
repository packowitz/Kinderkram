package de.pacworx.kinderkram;

import android.text.format.DateFormat;


public class HighscoreEntry {
  public int score;
  public String date;

  public HighscoreEntry(int score) {
    this.score = score;
    date = DateFormat.format("dd.MM.  kk:mm", new java.util.Date()).toString();
  }

  public HighscoreEntry(int score, String date) {
    this.score = score;
    this.date = date;
  }
}
