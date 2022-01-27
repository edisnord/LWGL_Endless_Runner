package Engine;

import Components.SpriteRenderer;
import Render.Texture;
import Util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(250, 250)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(300, 100), new Vector2f(250, 250)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage2.png")));
        this.addGameObjectToScene(obj2);

        loadResources();

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        System.out.println("FPS: " + (1.0f / dt));

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        moveCamera(dt);

        this.renderer.render();
    }

    private void moveCamera(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x -= dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x += dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.position.y -= dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.position.y += dt * 300.0f;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_1)){
            float x = 40.0f;
            float y = 22.0f;
        }
    }


}