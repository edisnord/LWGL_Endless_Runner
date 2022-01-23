package Engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene(){
    //To be filled
    }

    public void start(){
        for (GameObject g: gameObjects) {
            g.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go){
        if(!isRunning){
            gameObjects.add(go);
        } else if(isRunning){
            gameObjects.add(go);
            go.start();
        }
    }

    public abstract void init();

    public abstract void update(float dt);

}
