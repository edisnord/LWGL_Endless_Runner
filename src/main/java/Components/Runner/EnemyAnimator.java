package Components.Runner;

import Components.SpriteRenderer;
import Components.Spritesheet;
import Engine.KeyListener;
import Engine.Runner.Events;

public class EnemyAnimator extends Animator{
    public EnemyAnimator(Spritesheet spritesheet, boolean keyPressEvents) {
        super(spritesheet, keyPressEvents);
    }

    @Override
    public void start() {
        spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
    }

    @Override
    public void update(float dt) {

        int[] leftIndices = {
                0, 1, 2, 3, 4
        };
        if(!Events.gameOver)
        animateIdle(leftIndices, 0.05f, dt);

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
