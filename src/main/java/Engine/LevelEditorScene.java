package Engine;

import Components.*;
import Components.Runner.PlayerAnimator;
import Components.Runner.PlayerController;
import Engine.Runner.EnemyManager;
import Engine.Runner.Events;
import Engine.Runner.NumberRenderer;
import Render.Texture;
import Util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private EnemyManager enemyManager;
    private NumberRenderer numberRenderer;
    public List<Spritesheet> sprites;
    private List<GameObject> bgTiles = new ArrayList<>();
    List<GameObject> numbers = new ArrayList<>();
    Transform bgParent = new Transform(new Vector2f(0, 0), new Vector2f(0, 0));
    Transform scorePos = new Transform(new Vector2f(1080, 10), new Vector2f(120, 25));
    GameObject ob1 = new GameObject("Object 1", new Transform(new Vector2f(-100, 100), new Vector2f(128, 145)));

    public LevelEditorScene() {
        sprites = new ArrayList<>();

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        this.enemyManager = new EnemyManager(this, 2f);

        for(int i = -2; i < 14; i++){
            for(int j = 0; j < 7; j++){
                GameObject bg = new GameObject("bg 1", bgParent.addAndReturnChild(new Transform(new Vector2f(i * 100, j * 100), new Vector2f(100, 100))));
                bg.addComponent(new SpriteRenderer(sprites.get(1).getSprite(7)));
                bgTiles.add(bg);
                this.addGameObjectToScene(bg);
            }
        }

        var scoreText = new GameObject("scoreText", scorePos);
        scoreText.addComponent(new SpriteRenderer(new Sprite(new Texture("assets/images/score.png"))));
        addGameObjectToScene(scoreText);


        //number width: 20px
        //number height: 25px

        numberRenderer = new NumberRenderer(this, scorePos);

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
        AssetPool.addSpritesheet("assets/images/numberssheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/numberssheet.png"),
                        8, 8, 83, 0));

        sprites.add(0, AssetPool.getSpritesheet("assets/images/charactersheet.png"));
        sprites.add(1, AssetPool.getSpritesheet("assets/images/areasheet.png"));
        sprites.add(2, AssetPool.getSpritesheet("assets/images/wolfsheet1.png"));
        sprites.add(3, AssetPool.getSpritesheet("assets/images/numberssheet.png"));

    }

    int reps;

    @Override
    public void update(float dt) {

        if(!Events.gameOver) {
            autoScroll();
            bgParent.position.x -= 5;
        }
        moveCamera();
        System.out.println("FPS: " + (1.0f / dt));

        enemyManager.update(dt);

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        renderNumbers();

        this.renderer.render();
    }

    private void autoScroll(){
        reps++;

        if(reps % 10 == 0){

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
    }

    private void moveCamera(){
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x += 5;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x -= 5;
        }
    }

    private void renderNumbers(){
        int hundredth = 0;
        int tenth = 0;
        int single = 0;
        int mode = 0;

        for (GameObject go: numbers) {
            removeGameObjectFromScene(go);
            scorePos.deleteChild(go.transform);
            renderer.remove(go);
        }

        numbers.clear();

        String score = String.valueOf(Events.score);

        if(score.length() == 1) {
            single = score.charAt(0) - 48;
            mode = 0;
        }
        else if(score.length() == 2){
            single = score.charAt(1);
            hundredth = score.charAt(0);
            mode = 1;
        } else if (score.length() == 3){
            single = score.charAt(2);
            tenth = score.charAt(1);
            hundredth = score.charAt(0);
            mode = 2;
        }

        if(mode == 0){
            numbers.add(new GameObject("Single", scorePos.addAndReturnChild(new Transform(new Vector2f(20 + 120, 0), new Vector2f(20, 25)))));
            numbers.get(0).addComponent(new SpriteRenderer(sprites.get(3).getSprite(single + 16)));
        }

        for (GameObject go: numbers) {
            addGameObjectToScene(go);
        }

    }

}