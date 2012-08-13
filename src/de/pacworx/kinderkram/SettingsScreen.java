package de.pacworx.kinderkram;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import de.pacworx.Difficulty;
import de.pacworx.Settings;


public class SettingsScreen implements Screen {
  private static final int WIDTH = 320;
  private static final int HEIGHT = 480;
  private Game game;
  private Screen returnscreen;
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private Vector3 touchPoint = new Vector3();
  private Rectangle kindergarten;
  private Rectangle vorschule;
  private Rectangle schule_1_2;
  private Rectangle schule_3_4;

  public SettingsScreen(Game game, Screen returnScreen) {
    this.game = game;
    this.returnscreen = returnScreen;
    camera = new OrthographicCamera(WIDTH, HEIGHT);
    camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
    batch = new SpriteBatch(10);
    schule_3_4 = new Rectangle(0, 55, WIDTH, 50);
    schule_1_2 = new Rectangle(0, 105, WIDTH, 50);
    vorschule = new Rectangle(0, 155, WIDTH, 50);
    kindergarten = new Rectangle(0, 205, WIDTH, 50);
    Assets.init();
  }

  public void render(float delta) {
    GL10 gl = Gdx.graphics.getGL10();
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    batch.draw(Assets.settings, 0, 0, WIDTH, HEIGHT);
    batch.end();

    handleInput();
  }

  private void handleInput() {
    if (Gdx.input.justTouched()) {
      camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
      if (OverlapTester.pointInRectangle(kindergarten, touchPoint.x, touchPoint.y)) {
        Settings.setDifficulty(Difficulty.KINDERGARTEN);
      } else if (OverlapTester.pointInRectangle(vorschule, touchPoint.x, touchPoint.y)) {
        Settings.setDifficulty(Difficulty.VORSCHULE);
      } else if (OverlapTester.pointInRectangle(schule_1_2, touchPoint.x, touchPoint.y)) {
        Settings.setDifficulty(Difficulty.SCHULE_1_2);
      } else if (OverlapTester.pointInRectangle(schule_3_4, touchPoint.x, touchPoint.y)) {
        Settings.setDifficulty(Difficulty.SCHULE_3_4);
      }
      game.setScreen(returnscreen);
    }
  }

  public void dispose() {
    Assets.dispose();
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

  private static class Assets {
    public static Texture atlas;
    public static TextureRegion settings;

    public static void init() {
      atlas = new Texture(Gdx.files.internal("atlas.png"));
      settings = new TextureRegion(atlas, 0, 0, 320, 480);
    }

    public static void dispose() {
      atlas.dispose();
    }
  }
}
