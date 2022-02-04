package Engine;

import Components.*;
import Components.PlayerAnimator;
import Components.PlayerController;
import Util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

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
                bg.addComponent(new SpriteRenderer(sprites.get(1).getSprite(7)));
                this.addGameObjectToScene(bg);
            }
        }

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(128, 145)));
        obj1.addComponent(new SpriteRenderer(sprites.get(0).getSprite(18)));
        obj1.addComponent(new PlayerController(5f, 5f));
        obj1.addComponent(new PlayerAnimator(AssetPool.getSpritesheet("assets/images/charactersheet.png"), true));
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


    }
}