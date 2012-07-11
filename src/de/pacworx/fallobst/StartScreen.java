package de.pacworx.fallobst;

import android.widget.Toast;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import de.pacworx.Settings;
import de.pacworx.kinderkram.Highscore;
import de.pacworx.kinderkram.OverlapTester;


public class StartScreen implements Screen {
  private FallobstGame game;
  private Highscore highscore;
  private BitmapFont edoFont;
  private OrthographicCamera camera;
  private SpriteBatch batch;

  private Vector3 touchPoint;
  private Rectangle backBounds;
  private Rectangle difficultyBounds;

  public StartScreen(FallobstGame game) {
    this.game = game;

    highscore = new Highscore(Settings.getDifficulty(), true, 5, "apfelernte");
    edoFont = new BitmapFont(Gdx.files.internal("edo.fnt"), false);
    camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
    camera.position.set(World.WIDTH / 2, World.HEIGHT / 2, 0);
    batch = new SpriteBatch(100);

    touchPoint = new Vector3();
    backBounds = new Rectangle(40, 40, 130, 130);
    difficultyBounds = new Rectangle(0, World.HEIGHT - 100, World.WIDTH, 100);
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

    edoFont.draw(batch, "Apfelernte", 120, 650);

    batch.end();

    handleInput();
  }

  private void handleInput() {
    if (Gdx.input.justTouched()) {
      camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
      if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
        game.finish();
      }
      if (OverlapTester.pointInRectangle(difficultyBounds, touchPoint.x, touchPoint.y)) {
        Toast.makeText(game.getActivity().getApplicationContext(), "Choose Difficulty", Toast.LENGTH_SHORT).show();
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
