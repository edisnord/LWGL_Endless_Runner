package Engine;

public class Enemy extends GameObject{
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
        transform.position.x -= 10;

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
