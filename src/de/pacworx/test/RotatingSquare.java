package de.pacworx.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;


public class RotatingSquare implements ApplicationListener {
  private Mesh square;
  private OrthographicCamera camera;

  @Override
  public void create() {
    if (square == null) {
      square = new Mesh(true, 4, 4,
        new VertexAttribute(Usage.Position, 3, "a_position"),
        new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

      square.setVertices(
        new float[] {
          -0.5f, -0.5f, 0, Color.toFloatBits(128, 0, 0, 255),
          0.5f, -0.5f, 0, Color.toFloatBits(192, 0, 0, 255),
          -0.5f, 0.5f, 0, Color.toFloatBits(192, 0, 0, 255),
          0.5f, 0.5f, 0, Color.toFloatBits(255, 0, 0, 255)
        });
      square.setIndices(new short[] { 0, 1, 2, 3 });

    }

  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public void pause() {
  }

  @Override
  public void render() {
    camera.update();
    camera.apply(Gdx.gl10);

    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    square.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
  }

  @Override
  public void resize(int width, int height) {
    float aspectRatio = (float) width / (float) height;
    camera = new OrthographicCamera(2f * aspectRatio, 2f);
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }
}
