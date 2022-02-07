package Components.Runner;

import Engine.Component;
import Engine.GameObject;
import Engine.KeyListener;
import Engine.Runner.Events;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;

public class PlayerController extends Component {

    public float xVel, yVel;

    public PlayerController(float xVel, float yVel){
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public void update(float dt) {
        if(!Events.gameOver) {
            if (KeyListener.isKeyPressed(GLFW_KEY_UP) && gameObject.transform.position.y < 560) {
                gameObject.transform.position.y += yVel;
            }
            if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) && gameObject.transform.position.y > 0) {
                gameObject.transform.position.y -= yVel;
            }
        }
    }
}
