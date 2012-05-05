package de.pacworx.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class CameraPlayground implements ApplicationListener {
  static final int WIDTH = 480;
  static final int HEIGHT = 320;

  private OrthographicCamera cam;
  private Texture texture;
  private Mesh mesh;
  private Rectangle glViewport;
  private float rotationSpeed;
  Vector3 touchPos = new Vector3();

  @Override
  public void create() {
    rotationSpeed = 0.5f;
    mesh = new Mesh(true, 4, 6,
      new VertexAttribute(VertexAttributes.Usage.Position, 3, "attr_Position"),
      new VertexAttribute(Usage.TextureCoordinates, 2, "attr_texCoords"));
    texture = new Texture(Gdx.files.internal("test/sc_map.png"));
    mesh.setVertices(
      new float[] {
        -612, -612, 0, 0, 1,
        612, -612, 0, 1, 1,
        612, 612, 0, 1, 0,
        -612, 612, 0, 0, 0
      });
    mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });

    cam = new OrthographicCamera(WIDTH, HEIGHT);
    //cam.position.set(WIDTH / 2, HEIGHT / 2, 0);

    //    glViewport = new Rectangle(0, 0, WIDTH, HEIGHT);

  }

  @Override
  public void render() {
    handleInput();

    GL10 gl = Gdx.graphics.getGL10();

    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    //gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);

    cam.update();
    cam.apply(gl);

    // Texturing --------------------- /
    gl.glActiveTexture(GL10.GL_TEXTURE0);
    gl.glEnable(GL10.GL_TEXTURE_2D);
    texture.bind();

    mesh.render(GL10.GL_TRIANGLES);

  }

  private void handleInput() {
    if (Gdx.input.isTouched()) {
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

      cam.unproject(touchPos);

      //Log.d("TestGame", "WorldX:" + touchPos.x + "; WorldY:" + touchPos.y);
      touchPos.sub(cam.position);

      //Log.d("TestGame", "X:" + touchPos.x + "; Y:" + touchPos.y);
      if (touchPos.x < -120) {
        if (cam.position.x >= -369) {
          cam.translate(-3, 0, 0);
        }
      } else if (touchPos.x > 120) {
        if (cam.position.x <= 369) {
          cam.translate(3, 0, 0);
        }
      } else if (touchPos.y > 0) {
        if (cam.position.y <= 449) {
          cam.translate(0, 3, 0);
        }
      } else {
        if (cam.position.y >= -449) {
          cam.translate(0, -3, 0);
        }
      }
    }

    float rotate = Gdx.input.getAccelerometerY();
    if (Math.abs(rotate) > 2) {
      cam.rotate(rotationSpeed * rotate / 2, 0, 0, 1);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      cam.zoom += 0.02;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
      cam.zoom -= 0.02;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      if (cam.position.x > 0) {
        cam.translate(-3, 0, 0);
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      if (cam.position.x < 1024) {
        cam.translate(3, 0, 0);
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      if (cam.position.y > 0) {
        cam.translate(0, -3, 0);
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      if (cam.position.y < 1024) {
        cam.translate(0, 3, 0);
      }
    }
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      cam.rotate(-rotationSpeed, 0, 0, 1);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.E)) {
      cam.rotate(rotationSpeed, 0, 0, 1);
    }
  }

  @Override
  public void resize(int width, int height) {
    // TODO Auto-generated method stub
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
  }
}
