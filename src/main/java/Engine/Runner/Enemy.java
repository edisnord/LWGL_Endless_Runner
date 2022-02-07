package Engine.Runner;

import Engine.GameObject;
import Engine.Scene;
import Engine.Transform;

public class Enemy extends GameObject {
    private GameObject player;
    private Scene scene;

    public Enemy(String name) {
        super(name);
    }

    public Enemy(String name, Transform transform, Scene scene) {
        super(name, transform);
        this.scene = scene;
    }
    @Override
    public void update(float dt) {
        if(!Events.gameOver)
        transform.position.x -= 10;

        //Incredibly verbose collision checking
        if((this.transform.position.x + 50 >= player.transform.position.x && this.transform.position.x + 60 <= player.transform.position.x + player.transform.scale.x)
                && ((this.transform.position.y >= player.transform.position.y +10) && (this.transform.position.y <= player.transform.position.y + player.transform.scale.y-10)
                || (this.transform.position.y + this.transform.scale.y >= player.transform.position.y+10) && (this.transform.position.y + this.transform.scale.y <= player.transform.position.y + player.transform.scale.y-10)))
        {
            Events.gameOver = true;
        }



        //&& this.transform.position.y + this.transform.scale.y < player.transform.position.y + player.transform.scale.y))

        transform.update();

        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    @Override
    public void start() {

        player = scene.getGameObjectWithName("Player");
        if(player == null){
            assert false: "No GameObject with name Player";
        }

        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

}
