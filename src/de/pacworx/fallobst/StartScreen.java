package de.pacworx.fallobst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import de.pacworx.Settings;
import de.pacworx.kinderkram.HighscoreEntry;
import de.pacworx.kinderkram.OverlapTester;
import de.pacworx.kinderkram.SettingsScreen;


public class StartScreen implements Screen {
  private FallobstGame game;
  private BitmapFont edoFont;
  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Vector3 touchPoint;
  private Rectangle backBounds;
  private Rectangle difficultyBounds;

  private float titleWidth;

  private float hs_x;
  private float hs_y;

  public StartScreen(FallobstGame game) {
    this.game = game;

    edoFont = new BitmapFont(Gdx.files.internal("bubbles.fnt"), false);
    camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
    camera.position.set(World.WIDTH / 2, World.HEIGHT / 2, 0);
    batch = new SpriteBatch(100);

    titleWidth = edoFont.getBounds("Apfelernte").width;

    touchPoint = new Vector3();
    backBounds = new Rectangle(40, 40, 130, 130);
    difficultyBounds = new Rectangle(0, World.HEIGHT - 220, World.WIDTH, 220);
  }

  @Override
  public void render(float delta) {
    GL10 gl = Gdx.graphics.getGL10();
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();

    batch.draw(Assets.background, 0, 0, World.WIDTH, World.HEIGHT);
    batch.draw(Assets.back, 40, 40, 130, 130);

    edoFont.draw(batch, "Apfelernte", (World.WIDTH - titleWidth) / 2, 670);

    float textWidth = edoFont.getBounds(Settings.getDifficulty().description).width;
    edoFont.draw(batch, Settings.getDifficulty().description, (World.WIDTH - textWidth) / 2, 610);

    hs_y = World.HEIGHT - 200;
    for (int i = 0; i < game.highscore.getScores().size(); i++) {
      HighscoreEntry entry = game.highscore.getScores().get(i);
      batch.draw(Assets.getAppleByPosition(i + 1), 10, hs_y, 32, 32);
      hs_x = 120 - edoFont.getBounds("" + entry.score).width;
      edoFont.draw(batch, "" + entry.score, hs_x, hs_y + 32);
      batch.draw(Assets.apple, 125, hs_y, 32, 32);
      edoFont.draw(batch, entry.date, 200, hs_y + 32);
      hs_y -= 40;
    }

    batch.end();

    handleInput();
  }

  private void handleInput() {
    if (Gdx.input.justTouched()) {
      camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
      if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
        game.finish();
      } else if (OverlapTester.pointInRectangle(difficultyBounds, touchPoint.x, touchPoint.y)) {
        game.setScreen(new SettingsScreen(game, this));
      } else {
        game.setScreen(new Fallobst(game));
      }
    }
  }

  @Override
  public void dispose() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resize(int arg0, int arg1) {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void show() {
  }

}
