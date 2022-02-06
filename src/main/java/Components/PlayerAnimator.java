package Components;

import Components.Animator;
import Components.SpriteRenderer;
import Components.Spritesheet;
import Engine.KeyListener;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;

public class PlayerAnimator extends Animator {
    //These two variables need to remain global
    int lastPressedKey, currPressedKey;


    public PlayerAnimator(Spritesheet spritesheet, boolean keyPressEvents) {
        super(spritesheet, keyPressEvents);
    }

    @Override
    public void start() {
        spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
    }

    @Override
    public void update(float dt) {

        int[] upIndices = {
                0, 1
        };
        int[] rightIndices = {
                18, 19, 20
        };
        int[] downIndices = {
                37, 38
        };
        int[] leftIndices = {
                55, 56
        };

//        animateOnKeyHold(GLFW_KEY_UP, upIndices, 0.2f, dt);
//        animateOnKeyHold(GLFW_KEY_DOWN, downIndices, 0.2f, dt);
//        animateOnKeyHold(GLFW_KEY_LEFT, leftIndices, 0.2f, dt);
//        animateOnKeyHold(GLFW_KEY_RIGHT, rightIndices, 0.2f, dt);

          animateIdle(rightIndices, 0.1f, dt);

    }

    private void animateOnKeyHold(int keyPressed, int[] sequence, float speed, float dt) {

        float timeBetweenFrames = speed;


        if (KeyListener.isKeyPressed(keyPressed)) {
            animTempTime -= dt;
            if (animTempTime <= 0) {
                if (animIndex == sequence.length) animIndex = 0;

                spriteRenderer.setSprite(spritesheet.getSprite(sequence[animIndex]));

                animIndex++;

                animTempTime = timeBetweenFrames;

            }
        }


    }

    private void animateIdle(int[] sequence, float speed, float dt) {

        float timeBetweenFrames = speed;

            animTempTime -= dt;
            if (animTempTime <= 0) {
                if (animIndex == sequence.length) animIndex = 0;

                spriteRenderer.setSprite(spritesheet.getSprite(sequence[animIndex]));

                animIndex++;

                animTempTime = timeBetweenFrames;


        }


    }

}
