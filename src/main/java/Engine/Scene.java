package Engine;

import Render.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    public Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene() {

    }

    public abstract void init() ;

    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public GameObject getGameObjectWithName(String name){
        for (GameObject go: gameObjects) {
            if (go.getName() == name){
                return go;
            }
        }
        return null;
    }

    public void removeGameObjectFromScene(GameObject go){
        gameObjects.remove(go);
    }

    public abstract void update(float dt);

    public Camera camera() {
        return this.camera;
    }
}