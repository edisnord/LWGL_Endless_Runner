package Components.Runner;

import Components.SpriteRenderer;
import Components.Spritesheet;
import Engine.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class Animator extends Component {
    Spritesheet spritesheet;
    SpriteRenderer spriteRenderer;
    List<int[]> indices;
    boolean keyPressEvents;
    //These two variables need to remain global
    int animIndex;
    float animTempTime;

    public Animator(Spritesheet spritesheet, boolean keyPressEvents) {
        animTempTime = 0;
        indices = new ArrayList<>();
        animIndex = 0;
        this.spritesheet = spritesheet;
        this.keyPressEvents = keyPressEvents;
    }

    public float getAnimTempTime() {
        return animTempTime;
    }

    public void setAnimTempTime(float animTempTime) {
        this.animTempTime = animTempTime;
    }

    public int getAnimIndex() {
        return animIndex;
    }

    public void setAnimIndex(int animIndex) {
        this.animIndex = animIndex;
    }

}
