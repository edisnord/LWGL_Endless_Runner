package Engine;

import Components.PlayerController;
import Components.Sprite;
import Components.SpriteRenderer;
import Components.Spritesheet;
import Render.Texture;
import Util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;


    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
//        if(spriteFlipTimeLeft <= 0){
//            spriteFlipTimeLeft = spriteFlipTime;
//            spriteIndex++;
//            if(spriteIndex > 4) spriteIndex = 0;
//
//            SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
//            sr.setSprite(sprites.getSprite(spriteIndex));
//        }

        System.out.println("FPS: " + (1.0f / dt));



        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        moveCamera(dt);

        this.renderer.render();
    }

    private void moveCamera(float dt) {

        if (!KeyListener.isKeyPressed(GLFW_KEY_RIGHT) && !KeyListener.isKeyPressed(GLFW_KEY_LEFT) && !
                KeyListener.isKeyPressed(GLFW_KEY_UP) && !KeyListener.isKeyPressed(GLFW_KEY_DOWN)){
            SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
            sr.setSprite(sprites.getSprite(0));
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            obj1.transform.position.x += 3f;
            spriteFlipTimeLeft -= dt;
            if(spriteFlipTimeLeft <= 0){
                spriteFlipTimeLeft = spriteFlipTime;
                spriteIndex++;
                if(spriteIndex > 2) spriteIndex = 1;

                SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
                sr.setSprite(sprites.getSprite(spriteIndex));
            }

        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            obj1.transform.position.x -= 1f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            obj1.transform.position.y += 1f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            obj1.transform.position.y -= 1f;
        }
    }


}