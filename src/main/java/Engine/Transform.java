package Engine;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Transform {

    public Vector2f position;
    public Vector2f scale;
    public boolean isParent;
    public Transform parent;
    public List<Transform> children;

    private Vector2f relPosition;

    public void update(){
        if(!isParent){
            this.position = new Vector2f(parent.position.x + relPosition.x, parent.position.y + relPosition.y);
        } else {
            for (Transform t: children) {
                t.update();
            }
        }
    }

    public Transform() {
        isParent = true;
        init(new Vector2f(), new Vector2f());
        children = new ArrayList<>();
    }

    public Transform(Vector2f position, Vector2f scale) {
        isParent = true;
        init(position, scale);
        children = new ArrayList<>();
    }

    private Transform(Vector2f position, Vector2f scale, Transform parent){
        isParent = false;
        this.parent = parent;
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        if(isParent) {
            this.position = position;
            this.scale = scale;
        } else {
            this.relPosition = position;
            this.scale = scale;
            this.position = new Vector2f(parent.position.x + relPosition.x, parent.position.y + relPosition.y);
        }
    }

    public Transform copy(){
        return new Transform(new Vector2f(this.position.x), new Vector2f(this.position.y));
    }

    //sus
    public void copy(Transform to){
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    public Transform addAndReturnChild(Transform childe){
        Transform child = new Transform(childe.position, childe.scale, this);
        children.add(child);
        return child;
    }

    public void deleteChild(Transform child){
        children.remove(child);
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
}
