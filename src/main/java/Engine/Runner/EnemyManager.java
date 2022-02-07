package Engine.Runner;

import Components.Runner.EnemyAnimator;
import Components.SpriteRenderer;
import Engine.GameObject;
import Engine.Scene;
import Engine.Transform;
import Util.AssetPool;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private Scene scene;
    List<GameObject> enemies;
    public float interval;
    public float temp;

    public EnemyManager(Scene scene, float interval){
        init(scene, interval);
    }

    public void init(Scene scene,float interval){
        this.scene = scene;
        enemies = new ArrayList<>();
        this.interval = interval;
        temp = interval;
    }

    public void update(float dt){
        if(!Events.gameOver) {
            temp -= dt;
            Random random = new Random();
            if (temp <= 0) {
                GameObject nme = new Enemy("enemy", new Transform(new Vector2f(1200, random.nextInt(600)), new Vector2f(2 * 100, 2 * 50)), this.scene);
                nme.addComponent(new SpriteRenderer(AssetPool.getSpritesheet("assets/images/wolfsheet1.png").getSprite(5)));
                nme.addComponent(new EnemyAnimator(AssetPool.getSpritesheet("assets/images/wolfsheet1.png"), false));
                scene.addGameObjectToScene(nme);
                temp = interval;
            }
        }
        }

}
