package Engine.Runner;

import Components.SpriteRenderer;
import Engine.GameObject;
import Engine.LevelEditorScene;
import Engine.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class NumberRenderer {

    LevelEditorScene scene;
    int hundredth;
    int tenth;
    int single;
    int mode;
    Transform refToParent;
    List<GameObject> numbers;

    public NumberRenderer(LevelEditorScene scene, Transform parent){
        numbers = new ArrayList<>();
        refToParent = parent;
        this.scene = scene;
    }

    public void update(){

        for (GameObject go: numbers) {
            scene.removeGameObjectFromScene(go);
            refToParent.deleteChild(go.transform);
            scene.renderer.remove(go);
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
            numbers.add(new GameObject("Single", refToParent.addAndReturnChild(new Transform(new Vector2f(20 + 120, 0), new Vector2f(20, 25)))));
            numbers.get(0).addComponent(new SpriteRenderer(scene.sprites.get(3).getSprite(single + 16)));
        }

        for (GameObject go: numbers) {
            scene.addGameObjectToScene(go);
        }



    }

}
