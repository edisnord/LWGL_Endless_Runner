package Engine;

import Components.*;
import Components.PlayerAnimator;
import Components.PlayerController;
import Util.AssetPool;
import org.joml.Vector2f;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private EnemyManager enemyManager;
    private List<Spritesheet> sprites;
    private List<GameObject> bgTiles = new ArrayList<>();
    Transform bgParent = new Transform(new Vector2f(0, 0), new Vector2f(0, 0));
    GameObject ob1 = new GameObject("Object 1", new Transform(new Vector2f(-100, 100), new Vector2f(128, 145)));

    public LevelEditorScene() {
        sprites = new ArrayList<>();

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        this.enemyManager = new EnemyManager(this, 0f);

        for(int i = -2; i < 14; i++){
            for(int j = 0; j < 7; j++){
                GameObject bg = new GameObject("bg 1", bgParent.addAndReturnChild(new Transform(new Vector2f(i * 100, j * 100), new Vector2f(100, 100))));
                bg.addComponent(new SpriteRenderer(sprites.get(1).getSprite(7)));
                bgTiles.add(bg);
                this.addGameObjectToScene(bg);
            }
        }

        obj1 = new GameObject("Player", new Transform(new Vector2f(100, 100), new Vector2f(128, 145)));
        obj1.addComponent(new SpriteRenderer(sprites.get(0).getSprite(19)));
        obj1.addComponent(new PlayerController(10f, 10f));
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
        AssetPool.addSpritesheet("assets/images/wolfsheet1.png",
                new Spritesheet(AssetPool.getTexture("assets/images/wolfsheet1.png"),
                        64, 32, 15, 0));

        sprites.add(0, AssetPool.getSpritesheet("assets/images/charactersheet.png"));
        sprites.add(1, AssetPool.getSpritesheet("assets/images/areasheet.png"));
        sprites.add(2, AssetPool.getSpritesheet("assets/images/wolfsheet1.png"));

    }

    int reps;

    @Override
    public void update(float dt) {

        reps++;

        if(reps % 20 == 0){

            for(int j = 0; j < 7; j++){
                GameObject bg = new GameObject("bg" + j + "extra", bgParent.addAndReturnChild(new Transform(new Vector2f( 5 * reps + 1295 + ( 100 * (reps / 20f)), j * 100), new Vector2f(100, 100))));
                bg.addComponent(new SpriteRenderer(sprites.get(1).getSprite(7)));
                bgTiles.add(bg);
                this.addGameObjectToScene(bg);
            }

        }

        if(reps % 20 == 0){
            for (int i = 0; i < 7; i++) {
                GameObject go = bgTiles.get(i);
                bgTiles.remove(i);
                this.removeGameObjectFromScene(go);
                bgParent.deleteChild(go.transform);
                this.renderer.remove(go);

            }
        }

       bgParent.position.x -= 5;

        moveCamera();
        System.out.println("FPS: " + (1.0f / dt));

        enemyManager.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    private void moveCamera(){
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x += 5;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x -= 5;
        }
    }

}