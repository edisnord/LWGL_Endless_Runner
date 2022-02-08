package Engine;

import Render.Sprite;
import Components.SpriteRenderer;
import Components.Spritesheet;
import Engine.Runner.Events;
import Render.Texture;
import Util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

public class LevelScene extends Scene{

    GameObject gameOverText;
    GameObject restartNotifier;
    List<GameObject> numbers = new ArrayList<>();
    public List<Spritesheet> sprites;

    @Override
    public void init() {
        sprites = new ArrayList<>();

        loadResources();

        renderGameOver();

        var scoreText = new GameObject("scoreText", new Transform(new Vector2f(1080, 10), new Vector2f(120, 25)));
        scoreText.addComponent(new SpriteRenderer(new Sprite(new Texture("assets/images/score.png"))));
        addGameObjectToScene(scoreText);


        var num3 = new GameObject("digit3", new Transform(new Vector2f(1250, 5), new Vector2f(20, 30)));
        num3.addComponent(new SpriteRenderer(sprites.get(0).getSprite(16)));
        numbers.add(0, num3);
        addGameObjectToScene(num3);


        var num2 = new GameObject("digit2", new Transform(new Vector2f(1230, 5), new Vector2f(20, 30)));
        num2.addComponent(new SpriteRenderer(sprites.get(0).getSprite(16)));
        numbers.add(1, num2);
        addGameObjectToScene(num2);


        var num1 = new GameObject("digit1", new Transform(new Vector2f(1210, 5), new Vector2f(20, 30)));
        num1.addComponent(new SpriteRenderer(sprites.get(0).getSprite(16)));
        numbers.add(2, num1);
        addGameObjectToScene(num1);

        renderNumbers();

        renderRestart();

        this.camera = new Camera(new Vector2f(50, 0));
    }

    private void renderGameOver() {
        var G = new GameObject("G", new Transform(new Vector2f(250,300), new Vector2f(100, 200)));
        G.addComponent(new SpriteRenderer(sprites.get(0).getSprite(39)));
        addGameObjectToScene(G);

        var A = new GameObject("A", new Transform(new Vector2f(350,300), new Vector2f(100, 200)));
        A.addComponent(new SpriteRenderer(sprites.get(0).getSprite(33)));
        addGameObjectToScene(A);

        var M = new GameObject("M", new Transform(new Vector2f(450,300), new Vector2f(100, 200)));
        M.addComponent(new SpriteRenderer(sprites.get(0).getSprite(45)));
        addGameObjectToScene(M);

        var E = new GameObject("E", new Transform(new Vector2f(550,300), new Vector2f(100, 200)));
        E.addComponent(new SpriteRenderer(sprites.get(0).getSprite(37)));
        addGameObjectToScene(E);

        var O = new GameObject("O", new Transform(new Vector2f(750,300), new Vector2f(100, 200)));
        O.addComponent(new SpriteRenderer(sprites.get(0).getSprite(47)));
        addGameObjectToScene(O);

        var V = new GameObject("V", new Transform(new Vector2f(850,300), new Vector2f(100, 200)));
        V.addComponent(new SpriteRenderer(sprites.get(0).getSprite(54)));
        addGameObjectToScene(V);

        var E2 = new GameObject("E", new Transform(new Vector2f(950,300), new Vector2f(100, 200)));
        E2.addComponent(new SpriteRenderer(sprites.get(0).getSprite(37)));
        addGameObjectToScene(E2);

        var R = new GameObject("E", new Transform(new Vector2f(1050,300), new Vector2f(100, 200)));
        R.addComponent(new SpriteRenderer(sprites.get(0).getSprite(50)));
        addGameObjectToScene(R);
    }

    private void renderRestart(){
        var P = new GameObject("P", new Transform(new Vector2f(480,200), new Vector2f(50, 100)));
        P.addComponent(new SpriteRenderer(sprites.get(0).getSprite(48)));
        addGameObjectToScene(P);

        var dash = new GameObject("E", new Transform(new Vector2f(540,200), new Vector2f(50, 100)));
        dash.addComponent(new SpriteRenderer(sprites.get(0).getSprite(13)));
        addGameObjectToScene(dash);

        var R = new GameObject("E", new Transform(new Vector2f(600,200), new Vector2f(50, 100)));
        R.addComponent(new SpriteRenderer(sprites.get(0).getSprite(50)));
        addGameObjectToScene(R);

        var E = new GameObject("E", new Transform(new Vector2f(650,200), new Vector2f(50, 100)));
        E.addComponent(new SpriteRenderer(sprites.get(0).getSprite(37)));
        addGameObjectToScene(E);

        var S = new GameObject("E", new Transform(new Vector2f(700,200), new Vector2f(50, 100)));
        S.addComponent(new SpriteRenderer(sprites.get(0).getSprite(51)));
        addGameObjectToScene(S);

        var T = new GameObject("E", new Transform(new Vector2f(750,200), new Vector2f(50, 100)));
        T.addComponent(new SpriteRenderer(sprites.get(0).getSprite(52)));
        addGameObjectToScene(T);

        var A = new GameObject("A", new Transform(new Vector2f(800,200), new Vector2f(50, 100)));
        A.addComponent(new SpriteRenderer(sprites.get(0).getSprite(33)));
        addGameObjectToScene(A);

        var R2 = new GameObject("E", new Transform(new Vector2f(850,200), new Vector2f(50, 100)));
        R2.addComponent(new SpriteRenderer(sprites.get(0).getSprite(50)));
        addGameObjectToScene(R2);

        var T2 = new GameObject("E", new Transform(new Vector2f(900,200), new Vector2f(50, 100)));
        T2.addComponent(new SpriteRenderer(sprites.get(0).getSprite(52)));
        addGameObjectToScene(T2);

    }

    @Override
    public void update(float dt) {

        System.out.println("FPS: " + (1.0f / dt));

        if(KeyListener.isKeyPressed(GLFW_KEY_P)){
            Events.score = 0;
            Events.gameOver = false;
            Events.firstPlay = false;
            renderer.batches.clear();
            Window.changeScene(0);
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }


        this.renderer.render();


    }

    private void loadResources(){

        sprites.add(0, AssetPool.getSpritesheet("assets/images/numberssheet.png"));

    }

    private void renderNumbers(){
        int hundredth = 0;
        int tenth = 0;
        int single = 0;
        int mode = 0;

        String score = String.valueOf(Events.score);

        if(score.length() == 1) {
            single = score.charAt(0) - 32;
            mode = 0;
        }
        else if(score.length() == 2){
            single = score.charAt(1) - 32;
            tenth = score.charAt(0) - 32;
            mode = 1;
        } else if (score.length() == 3){
            single = score.charAt(2) - 32;
            tenth = score.charAt(1) - 32;
            hundredth = score.charAt(0) - 32;
            mode = 2;
        }

        if(mode == 0){
            numbers.get(0).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(single));
        } else if (mode == 1){
            numbers.get(0).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(single));
            numbers.get(1).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(tenth));
        } else if(mode == 2){
            numbers.get(0).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(single));
            numbers.get(1).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(tenth));
            numbers.get(2).getComponent(SpriteRenderer.class).setSprite(sprites.get(0).getSprite(hundredth));
        }

    }

}
