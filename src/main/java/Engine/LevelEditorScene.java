package Engine;

import Components.PlayerController;
import Components.Sprite;
import Components.SpriteRenderer;
import Components.Spritesheet;
import Render.Texture;
import Util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private List<Spritesheet> sprites;


    public LevelEditorScene() {
        sprites = new ArrayList<>();

    }

    @Override
    public void init() {
        loadResources();



        this.camera = new Camera(new Vector2f(-250, 0));

        for(int i = -4; i < 25; i++){
            for(int j = 0; j < 14; j++){
                GameObject bg = new GameObject("bg 1", new Transform(new Vector2f(i * 100, j * 100), new Vector2f(100, 100)));
                bg.addComponent(new SpriteRenderer(sprites.get(1).getSprite(11)));
                this.addGameObjectToScene(bg);
            }
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 145)));
        obj1.addComponent(new SpriteRenderer(sprites.get(0).getSprite(18)));
        this.addGameObjectToScene(obj1);


    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.addSpritesheet("assets/images/charactersheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/charactersheet.png"),
                        16, 18, 72, 0));
        AssetPool.addSpritesheet("assets/images/areasheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/areasheet.png"),
                        16, 16, 30, 0));

        sprites.add(0, AssetPool.getSpritesheet("assets/images/charactersheet.png"));
        sprites.add(1, AssetPool.getSpritesheet("assets/images/areasheet.png"));

    }

    private int spriteIndex = 18;
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

//        if (!KeyListener.isKeyPressed(GLFW_KEY_RIGHT) && !KeyListener.isKeyPressed(GLFW_KEY_LEFT) && !
//                KeyListener.isKeyPressed(GLFW_KEY_UP) && !KeyListener.isKeyPressed(GLFW_KEY_DOWN)){
//            SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
//            sr.setSprite(sprites.getSprite(0));
//        }

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {

            obj1.transform.position.x += 3f;
            spriteFlipTimeLeft -= dt;
            if(spriteFlipTimeLeft <= 0){
                spriteFlipTimeLeft = spriteFlipTime;
                spriteIndex++;
                if(spriteIndex > 19 || spriteIndex < 18) spriteIndex = 18;

                SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
                sr.setSprite(sprites.get(0).getSprite(spriteIndex));
            }

        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            obj1.transform.position.x -= 1f;
            spriteFlipTimeLeft -= dt;
            if(spriteFlipTimeLeft <= 0){
                spriteFlipTimeLeft = spriteFlipTime;
                spriteIndex++;
                if(spriteIndex > 55 || spriteIndex < 54) spriteIndex = 54;

                SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
                sr.setSprite(sprites.get(0).getSprite(spriteIndex));
            }
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {

            obj1.transform.position.y += 1f;
            spriteFlipTimeLeft -= dt;
            if(spriteFlipTimeLeft <= 0){
                spriteFlipTimeLeft = spriteFlipTime;
                spriteIndex++;
                if(spriteIndex > 2) spriteIndex = 0;

                SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
                sr.setSprite(sprites.get(0).getSprite(spriteIndex));
            }
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            obj1.transform.position.y -= 1f;
            spriteFlipTimeLeft -= dt;
            if(spriteFlipTimeLeft <= 0){
                spriteFlipTimeLeft = spriteFlipTime;
                spriteIndex++;
                if(spriteIndex > 37 || spriteIndex < 36) spriteIndex = 36;

                SpriteRenderer sr = obj1.getComponent(SpriteRenderer.class);
                sr.setSprite(sprites.get(0).getSprite(spriteIndex));
            }
        }
    }


}