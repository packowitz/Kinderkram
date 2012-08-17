package de.pacworx.farbkleckse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import de.pacworx.Settings;
import de.pacworx.kinderkram.Fonts;
import de.pacworx.kinderkram.HighscoreEntry;
import de.pacworx.kinderkram.OverlapTester;
import de.pacworx.kinderkram.SettingsScreen;


public class StartScreen implements Screen {
  private FarbkleckseGame game;
  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Vector3 touchPoint;
  private Rectangle backBounds;
  private Rectangle difficultyBounds;

  private float titleWidth;

  private float time = 0;
  private int color = 0;
  private int xOffset = 0;
  private int yOffset = 0;

  private StringBuffer timeText = new StringBuffer();
  private float hs_y;

  public StartScreen(FarbkleckseGame game) {
    this.game = game;
    camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
    camera.position.set(World.WIDTH / 2, World.HEIGHT / 2, 0);
    batch = new SpriteBatch(100);

    titleWidth = Fonts.title_red.getBounds("Is ja'n Klecks").width;

    touchPoint = new Vector3();
    backBounds = new Rectangle(40, 30, 110, 110);
    difficultyBounds = new Rectangle(0, World.HEIGHT - 250, World.WIDTH, 250);
  }

  public void render(float delta) {
    if (delta > 0.04) {
      delta = 0.04f;
    }

    GL10 gl = Gdx.graphics.getGL10();
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    batch.draw(Assets.background, 0, 0, World.WIDTH, World.HEIGHT);

    Fonts.title_red.draw(batch, "Is ja'n Klecks", (World.WIDTH - titleWidth) / 2, 670);

    float textWidth = Fonts.level_red.getBounds("Stufe: " + Settings.getDifficulty().description).width;
    Fonts.level_red.draw(batch, "Stufe: " + Settings.getDifficulty().description, (World.WIDTH - textWidth) / 2,
      580);

    batch.draw(Assets.back, 40, 30, 110, 110);
    batch.draw(Assets.blob_los, 240, 30, 220, 110);

    if (game.highscore.isEmpty()) {
      updateWorld(delta);
      batch.draw(Assets.getBucket(color), 350, 420, 90, 90);
      if (time <= 4.4) {
        batch.draw(Assets.getBlob(color), 30 + xOffset, 210 + yOffset, 90, 90);
      }
      batch.draw(Assets.hand, 45 + xOffset, 185 + yOffset, 90, 90);
    } else {
      hs_y = World.HEIGHT - 280;
      for (int i = 0; i < game.highscore.getScores().size(); i++) {
        HighscoreEntry entry = game.highscore.getScores().get(i);
        batch.draw(Assets.getHighscore(i + 1), 10, hs_y, 90, 90);
        timeText.setLength(0);

        int seconds = entry.score / 10;
        timeText.append((int) (seconds / 60)).append(':');
        seconds = seconds % 60;
        if (seconds < 10) {
          timeText.append('0');
        }
        timeText.append(seconds);
        timeText.append('.');
        timeText.append((int) (entry.score % 10));
        timeText.append("  ").append(entry.date);
        Fonts.highscore_grey.draw(batch, timeText.toString(), 110, hs_y + 61);
        hs_y -= 100;
      }
    }

    batch.end();
    handleInput();
  }

  private void updateWorld(float delta) {
    time += delta;
    if ((time > 1) && (time <= 4)) {
      xOffset = (int) (100f * (time - 1f));
      yOffset = (int) (70f * (time - 1f));
    } else if (time >= 5.2) {
      time -= 5.2;
      nextColor();
      xOffset = 0;
      yOffset = 0;
    }
  }

  private void nextColor() {
    if (color < 3) {
      color++;
    } else {
      color = 0;
    }
  }

  private void handleInput() {
    if (Gdx.input.justTouched()) {
      camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
      if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
        game.finish();
      } else if (OverlapTester.pointInRectangle(difficultyBounds, touchPoint.x, touchPoint.y)) {
        game.setScreen(new SettingsScreen(game, this));
      } else {
        game.setScreen(new GameScreen(game));
      }
    }
  }

  public void dispose() {
  }

  public void hide() {
  }

  public void pause() {
  }

  public void resize(int arg0, int arg1) {
  }

  public void resume() {
  }

  public void show() {
  }

}
