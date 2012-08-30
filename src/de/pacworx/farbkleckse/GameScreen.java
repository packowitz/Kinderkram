package de.pacworx.farbkleckse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pacworx.kinderkram.Fonts;


public class GameScreen implements Screen {
  private FarbkleckseGame game;
  private World world;
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private StringBuffer timeText = new StringBuffer();
  private float textWidth = 0;

  public GameScreen(FarbkleckseGame game) {
    this.game = game;
    camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
    camera.position.set(World.WIDTH / 2, World.HEIGHT / 2, 0);
    this.world = new World(game, camera);
    batch = new SpriteBatch(100);
  }

  public void render(float delta) {
    if (delta > 0.04) {
      delta = 0.04f;
    }
    world.update(delta);

    GL10 gl = Gdx.graphics.getGL10();
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    batch.draw(Assets.background, 0, 0, World.WIDTH, World.HEIGHT);

    //Buckets
    drawBucket(Assets.bucket_blue, world.colorAreas[0]);
    drawBucket(Assets.bucket_green, world.colorAreas[1]);
    drawBucket(Assets.bucket_red, world.colorAreas[2]);
    drawBucket(Assets.bucket_yellow, world.colorAreas[3]);

    //Times and Points
    timeText.setLength(0);
    timeText.append((int) (world.time / 60)).append(':');
    if ((world.time % 60) < 10) {
      timeText.append('0');
    }
    timeText.append((int) (world.time % 60));
    if (world.penaltySum > 0) {
      timeText.append(" + ");
      timeText.append(world.penaltySum);
    }
    if (world.penaltyTime > 0) {
      textWidth = Fonts.calibri_red.getBounds(timeText.toString()).width;
      Fonts.calibri_red.draw(batch, timeText.toString(), (World.WIDTH - textWidth) / 2, 705);
    } else {
      textWidth = Fonts.calibri_brown.getBounds(timeText.toString()).width;
      Fonts.calibri_brown.draw(batch, timeText.toString(), (World.WIDTH - textWidth) / 2, 705);
    }
    textWidth = Fonts.calibri_brown.getBounds(Integer.toString(world.successCounter)).width;
    Fonts.calibri_brown.draw(batch, Integer.toString(world.successCounter), (World.WIDTH - textWidth) / 2, 39);

    if (world.state != World.STATE_GAME_OVER) {
      batch.draw(Assets.getBlob(world.blobColor), world.blobX, world.blobY, 90, 90);
    } else {
      timeText.setLength(0);
      timeText.append((int) ((world.time + world.penaltySum) / 60)).append(':');
      if (((world.time + world.penaltySum) % 60) < 10) {
        timeText.append('0');
      }
      timeText.append((int) ((world.time + world.penaltySum) % 60));
      timeText.append('.');
      timeText.append((int) ((world.time * 10f) % 10));
      textWidth = Fonts.calibri_brown_big.getBounds(timeText.toString()).width;
      Fonts.calibri_brown_big.draw(batch, timeText.toString(), (World.WIDTH - textWidth) / 2, 395);
      if ((world.finishedPosition >= 1) && (world.finishedPosition <= 4)) {
        batch.draw(Assets.getHighscore(world.finishedPosition), 160, 400, 160, 160);
      }
    }

    batch.end();
  }

  private void drawBucket(TextureRegion bucket, int position) {
    switch (position) {
      case 0: {
        batch.draw(bucket, 10, 620, 90, 90);
        break;
      }

      case 1: {
        batch.draw(bucket, 380, 620, 90, 90);
        break;
      }

      case 2: {
        batch.draw(bucket, 10, 10, 90, 90);
        break;
      }

      case 3: {
        batch.draw(bucket, 380, 10, 90, 90);
      }
    }
  }

  public void dispose() {
  }

  public void hide() {
  }

  public void pause() {
    world.pause();
  }

  public void resize(int arg0, int arg1) {
  }

  public void resume() {
    world.resume();
  }

  public void show() {
  }
}
