package de.pacworx;

public enum Difficulty {
  KINDERGARTEN("0", "Kindergarten"), VORSCHULE("1", "Vorschule"), SCHULE_1_2("2", "1. oder 2. Klasse"),
  SCHULE_3_4("3", "3. oder 4. Klasse");

  public String id;
  public String description;

  private Difficulty(String id, String description) {
    this.id = id;
    this.description = description;
  }

  public static Difficulty getById(String id) {
    for (Difficulty difficulty : values()) {
      if (difficulty.id.equals(id)) {
        return difficulty;
      }
    }
    return null;
  }
}
