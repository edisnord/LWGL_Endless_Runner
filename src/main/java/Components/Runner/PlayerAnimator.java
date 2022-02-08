package Components.Runner;

import Components.Runner.Animator;
import Components.SpriteRenderer;
import Components.Spritesheet;
import Engine.KeyListener;
import Engine.Runner.Events;

public class PlayerAnimator extends Animator {


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
                18, 19, 20, 19
        };
        int[] downIndices = {
                37, 38
        };
        int[] leftIndices = {
                55, 56
        };

        if(!Events.gameOver)
          animateIdle(rightIndices, 0.07f, dt);

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
