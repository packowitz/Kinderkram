package de.pacworx.fallobst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.pacworx.kinderkram.Fonts;


public class Fallobst implements Screen {
  private FallobstGame game;
  private World world;
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private MovingObject movingScore;
  private MovingObject movingBasket;

  public Fallobst(FallobstGame game) {
    this.game = game;
    world = new World(game.highscore);
    camera = new OrthographicCamera(World.WIDTH, World.HEIGHT);
    camera.position.set(World.WIDTH / 2, World.HEIGHT / 2, 0);
    batch = new SpriteBatch(100);
  }

  public void render(float delta) {
    if (world.state == World.STATE_LIVING) {
      world.update(delta, Gdx.input.getAccelerometerX());
    }

    GL10 gl = Gdx.graphics.getGL10();
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    batch.draw(Assets.background, 0, 0, World.WIDTH, World.HEIGHT);

    if (world.state != World.STATE_GAME_OVER) {
      batch.draw(Assets.basket, world.basket.position.x, world.basket.position.y, Basket.SIZE, Basket.SIZE);

      for (Vector2 vec : world.staticApples) {
        batch.draw(Assets.apple, vec.x, vec.y, Apple.APPLE_SIZE, Apple.APPLE_SIZE);
      }

      for (Apple apple : world.apples) {
        if (apple.state != Apple.STATE_FALLING) {
          renderApple(batch, apple);
        }
      }
      for (Apple apple : world.apples) {
        if (apple.state == Apple.STATE_FALLING) {
          renderApple(batch, apple);
        }
      }

      for (Bonus bonus : world.bonuses) {
        Fonts.calibri_gold.draw(batch, "+1", bonus.position.x - 16, bonus.position.y + 16);
      }
    } else {
      //Game Over
      if (movingBasket == null) {
        movingBasket = new MovingObject(world.basket.position.x, world.basket.position.y, 190, 310, 2);
      }
      movingBasket.update(delta);
      batch.draw(Assets.basket, movingBasket.position.x, movingBasket.position.y, Basket.SIZE, Basket.SIZE);
    }

    batch.draw(Assets.apple, 20, 675, 32, 32);
    if (world.state != World.STATE_GAME_OVER) {
      Fonts.calibri_gold.draw(batch, ": ", 54, 706);
      Fonts.calibri_gold.draw(batch, "" + world.applesCollected, 64, 704);
    } else {
      //Game Over
      if (movingScore == null) {
        float textWidth = Fonts.calibri_gold.getBounds("" + world.applesCollected).width;
        movingScore = new MovingObject(64, 704, 240 - (textWidth / 2), 360, 2);
      }
      movingScore.update(delta);
      Fonts.calibri_gold.draw(batch, "" + world.applesCollected, movingScore.position.x, movingScore.position.y);
    }

    //    if (Gdx.input.isTouched()) {
    //      Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    //      camera.unproject(vec);
    //      font.draw(batch, "X: " + vec.x + "; Y: " + vec.y, 10, 40);
    //    }
    batch.end();

    if (Gdx.input.justTouched() && (world.state == World.STATE_GAME_OVER)) {
      game.setScreen(new StartScreen(game));
    }
  }

  private void renderApple(SpriteBatch batch, Apple apple) {
    batch.draw(apple.isRotten ? Assets.apple_brown : Assets.apple,
      apple.position.x - (apple.size / 2) + apple.shakleOffset,
      apple.position.y - (apple.size / 2), apple.size, apple.size);
  }

  public void dispose() {
  }

  public void pause() {
    world.pause();
  }

  public void resize(int width, int height) {
  }

  public void resume() {
    world.resume();
  }

  public void hide() {
  }

  public void show() {
  }

}
